package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.dtos.NcmResponseDto;

public interface NcmService {

    /**
     * Identifica o código NCM de um produto pelo nome utilizando IA.
     *
     * @param productName nome do produto a ser classificado
     * @return DTO com o código NCM, descrição e nível de confiança
     * @throws RuntimeException se a resposta da IA não for um JSON válido
     */
    NcmResponseDto findNcm(String productName);
}
