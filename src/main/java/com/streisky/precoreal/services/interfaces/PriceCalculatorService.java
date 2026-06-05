package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.dtos.PriceCalculationRequestDto;
import com.streisky.precoreal.dtos.PriceCalculationResponseDto;

public interface PriceCalculatorService {

    /**
     * Calcula o preço sem impostos com base no NCM, UF e preço informados.
     *
     * @param request dados com NCM, UF, preço e flag de produto importado
     * @return DTO com preço original, preço sem imposto, valor do imposto e alíquotas por esfera
     */
    PriceCalculationResponseDto calculate(PriceCalculationRequestDto request);
}
