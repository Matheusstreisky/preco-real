package com.streisky.precoreal.parsers;

import com.streisky.precoreal.model.Ibpt;
import com.streisky.precoreal.model.IbptId;
import com.streisky.precoreal.parsers.interfaces.IbptParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CsvIbptParser implements IbptParser {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final int COLUNA_NCM                      = 0;
    private static final int COLUNA_EXCECAO_TARIFARIA        = 1;
    private static final int COLUNA_TIPO                     = 2;
    private static final int COLUNA_DESCRICAO                = 3;
    private static final int COLUNA_ALIQUOTA_NACIONAL        = 4;
    private static final int COLUNA_ALIQUOTA_IMPORTADO       = 5;
    private static final int COLUNA_ALIQUOTA_ESTADUAL        = 6;
    private static final int COLUNA_ALIQUOTA_MUNICIPAL       = 7;
    private static final int COLUNA_VIGENCIA_INICIO          = 8;
    private static final int COLUNA_VIGENCIA_FIM             = 9;
    private static final int COLUNA_CHAVE                    = 10;
    private static final int COLUNA_VERSAO                   = 11;
    private static final int COLUNA_FONTE                    = 12;

    /**
     * @param csv conteúdo do arquivo CSV da tabela IBPT (ISO-8859-1 já decodificado)
     * @param uf  código do estado (ex: SP, RJ)
     */
    public List<Ibpt> parse(String csv, String uf) {
        String[] lines = csv.split("\n");
        List<Ibpt> entries = new ArrayList<>(lines.length);
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            try {
                entries.add(parseLine(line, uf.toUpperCase()));
            } catch (Exception e) {
                log.warn("Linha {} da UF {} ignorada: {}", i, uf, e.getMessage());
            }
        }
        return entries;
    }

    private Ibpt parseLine(String line, String uf) {
        String[] p = line.split(";", -1);
        Ibpt entry = new Ibpt();
        entry.setId(new IbptId(p[COLUNA_NCM].trim(), uf));
        entry.setTipo(p[COLUNA_TIPO].trim());
        entry.setDescricao(p[COLUNA_DESCRICAO].trim());
        entry.setAliquotaNacional(decimal(p[COLUNA_ALIQUOTA_NACIONAL]));
        entry.setAliquotaImportado(decimal(p[COLUNA_ALIQUOTA_IMPORTADO]));
        entry.setAliquotaEstadual(decimal(p[COLUNA_ALIQUOTA_ESTADUAL]));
        entry.setAliquotaMunicipal(decimal(p[COLUNA_ALIQUOTA_MUNICIPAL]));
        entry.setVigenciaInicio(date(p[COLUNA_VIGENCIA_INICIO]));
        entry.setVigenciaFim(date(p[COLUNA_VIGENCIA_FIM]));
        entry.setChave(p[COLUNA_CHAVE].trim());
        entry.setVersao(p[COLUNA_VERSAO].trim());
        entry.setFonte(p.length > COLUNA_FONTE ? p[COLUNA_FONTE].trim() : null);
        return entry;
    }

    private BigDecimal decimal(String s) {
        return new BigDecimal(s.trim().replace(",", "."));
    }

    private LocalDate date(String s) {
        try {
            return LocalDate.parse(s.trim(), DATE_FMT);
        } catch (Exception e) {
            return null;
        }
    }
}
