package com.streisky.precoreal.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.models.IbptId;
import com.streisky.precoreal.parsers.interfaces.IbptParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Primary
@Component
public class JsonIbptParser implements IbptParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Ibpt> parse(String json, String uf) {
        try {
            JsonNode root = objectMapper.readTree(json);
            if (root.isArray()) {
                return parseArray(root, uf);
            }
            JsonNode ncmNode = root.path("ncm");
            if (ncmNode.isArray()) {
                return parseArray(ncmNode, uf);
            }
            return List.of(parseNode(root, uf));
        } catch (Exception e) {
            throw new IllegalArgumentException("JSON inválido para UF " + uf + ": " + e.getMessage(), e);
        }
    }

    private List<Ibpt> parseArray(JsonNode array, String uf) {
        List<Ibpt> entries = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            try {
                entries.add(parseNode(array.get(i), uf));
            } catch (Exception e) {
                log.warn("Entrada {} da UF {} ignorada: {}", i, uf, e.getMessage());
            }
        }
        return entries;
    }

    private Ibpt parseNode(JsonNode node, String ufFallback) {
        String uf = texto(node, "uf", ufFallback).toUpperCase();

        Ibpt ibpt = new Ibpt();
        ibpt.setId(new IbptId(texto(node, "codigo", ""), uf));
        ibpt.setTipo(String.valueOf(node.path("tipo").asInt()));
        ibpt.setDescricao(texto(node, "descricao", ""));
        ibpt.setAliquotaNacional(decimal(node, "nacionalfederal"));
        ibpt.setAliquotaImportado(decimal(node, "importadosfederal"));
        ibpt.setAliquotaEstadual(decimal(node, "estadual"));
        ibpt.setAliquotaMunicipal(decimal(node, "municipal"));
        ibpt.setVigenciaInicio(data(node, "vigenciainicio"));
        ibpt.setVigenciaFim(data(node, "vigenciafim"));
        ibpt.setVersao(texto(node, "versao", null));
        ibpt.setFonte(texto(node, "fonte", null));
        return ibpt;
    }

    private String texto(JsonNode node, String campo, String valorPadrao) {
        JsonNode valor = node.path(campo);
        return valor.isNull() || valor.isMissingNode() ? valorPadrao : valor.asText();
    }

    private BigDecimal decimal(JsonNode node, String campo) {
        return new BigDecimal(node.path(campo).asText("0"));
    }

    private LocalDate data(JsonNode node, String campo) {
        String valor = texto(node, campo, null);
        if (valor == null || valor.isBlank()) return null;
        try {
            return LocalDate.parse(valor);
        } catch (Exception e) {
            return null;
        }
    }
}
