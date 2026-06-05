package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.PriceCalculationRequestDto;
import com.streisky.precoreal.dtos.PriceCalculationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Calculadora de Preços", description = "Cálculo de preço sem impostos com base no NCM e UF")
public interface PriceCalculatorController {

    @Operation(summary = "Calcular preço sem impostos", description = "Calcula o preço sem impostos com base no NCM, UF e preço informados.")
    @ApiResponse(responseCode = "200", description = "Preço original, preço sem imposto, valor do imposto e detalhamento das alíquotas")
    @ApiResponse(responseCode = "404", description = "NCM/UF não encontrado na tabela IBPT")
    PriceCalculationResponseDto calculate(@RequestBody PriceCalculationRequestDto request);
}
