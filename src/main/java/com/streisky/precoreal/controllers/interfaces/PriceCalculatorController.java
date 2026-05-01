package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.PriceCalculationRequestDto;
import com.streisky.precoreal.dtos.PriceCalculationResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface PriceCalculatorController {

    PriceCalculationResponseDto calculate(@RequestBody PriceCalculationRequestDto request);
}
