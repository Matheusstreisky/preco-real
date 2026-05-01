package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.dto.PriceCalculationRequestDto;
import com.streisky.precoreal.dto.PriceCalculationResponseDto;

public interface PriceCalculatorService {

    PriceCalculationResponseDto calculate(PriceCalculationRequestDto request);
}
