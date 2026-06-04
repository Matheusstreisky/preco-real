package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.NcmResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface NcmController {

    /**
     * Identifica o código NCM de um produto pelo nome utilizando IA.
     *
     * @param productName nome do produto a ser classificado
     * @return DTO com o código NCM, descrição e nível de confiança
     */
    NcmResponseDto findNcm(@RequestParam String productName);
}
