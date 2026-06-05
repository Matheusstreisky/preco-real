package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.NcmResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "NCM", description = "Identificação de código NCM por nome de produto utilizando IA")
public interface NcmController {

    @Operation(summary = "Identificar NCM por produto", description = "Utiliza IA para identificar o código NCM de um produto pelo nome.")
    @ApiResponse(responseCode = "200", description = "NCM identificado com código, descrição e nível de confiança")
    @ApiResponse(responseCode = "500", description = "Falha na comunicação com o provedor de IA")
    NcmResponseDto findNcm(
            @Parameter(description = "Nome do produto a ser classificado", example = "Notebook Dell Inspiron") @RequestParam String productName);
}
