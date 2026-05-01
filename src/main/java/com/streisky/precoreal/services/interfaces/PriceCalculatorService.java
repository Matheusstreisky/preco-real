package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.dtos.PriceCalculationRequestDto;
import com.streisky.precoreal.dtos.PriceCalculationResponseDto;

public interface PriceCalculatorService {

    PriceCalculationResponseDto calculate(PriceCalculationRequestDto request);
}
