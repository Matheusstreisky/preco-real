package com.streisky.precoreal.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streisky.precoreal.clients.interfaces.AiClient;
import com.streisky.precoreal.dto.NcmResponseDto;
import com.streisky.precoreal.services.interfaces.NcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NcmServiceImpl implements NcmService {

    private static final String NCM_SYSTEM_PROMPT = """
        Você é um especialista em classificação tributária brasileira (NCM — Nomenclatura Comum do Mercosul/TIPI).
        Dado o nome de um produto, identifique o código NCM de 8 dígitos mais adequado.

        Exemplos:
        - "Smartphone" → {"ncm":"85171299","descricao":"Outros aparelhos telefônicos","confianca":"alta"}
        - "Notebook" → {"ncm":"84713012","descricao":"Máquinas automáticas para processamento de dados, portáteis","confianca":"alta"}
        - "Televisão LED" → {"ncm":"85287210","descricao":"Aparelhos receptores de televisão colorida","confianca":"alta"}
        - "Arroz 5kg" → {"ncm":"10063020","descricao":"Arroz semibranqueado ou branqueado","confianca":"alta"}
        - "Pneu automóvel" → {"ncm":"40111000","descricao":"Pneus novos de borracha para automóveis","confianca":"alta"}
        - "Cerveja lata" → {"ncm":"22030000","descricao":"Cervejas de malte","confianca":"alta"}
        - "Sabão em pó" → {"ncm":"34022000","descricao":"Preparações tensoativas para lavar","confianca":"alta"}
        - "Cadeira escritório" → {"ncm":"94013000","descricao":"Assentos giratórios de altura ajustável","confianca":"alta"}
        - "Frango congelado" → {"ncm":"02071200","descricao":"Galos e galinhas congelados, não cortados","confianca":"alta"}
        - "Câmera digital" → {"ncm":"90065310","descricao":"Câmeras digitais","confianca":"alta"}

        Responda APENAS com JSON válido, sem texto adicional:
        {"ncm":"XXXXXXXX","descricao":"descrição da categoria NCM","confianca":"alta|media|baixa"}
        """;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AiClient aiClient;

    @Override
    public NcmResponseDto findNcm(String productName) {
        String json = aiClient.chat(NCM_SYSTEM_PROMPT, "Produto: " + productName);
        try {
            return objectMapper.readValue(json, NcmResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Resposta inválida do provedor de IA para NCM: " + json, e);
        }
    }
}
