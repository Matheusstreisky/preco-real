package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.PriceCalculationRequestDto;
import com.streisky.precoreal.dtos.PriceCalculationResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface PriceCalculatorController {

    /**
     * Calcula o preço sem impostos com base no NCM, UF e preço informados.
     *
     * @param request dados com NCM, UF, preço e flag de produto importado
     * @return DTO com preço original, preço sem imposto, valor do imposto e alíquotas
     */
    PriceCalculationResponseDto calculate(@RequestBody PriceCalculationRequestDto request);
}
