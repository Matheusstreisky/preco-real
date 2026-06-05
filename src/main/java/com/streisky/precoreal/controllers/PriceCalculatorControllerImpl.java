package com.streisky.precoreal.controllers;

import com.streisky.precoreal.controllers.interfaces.PriceCalculatorController;
import com.streisky.precoreal.dtos.PriceCalculationRequestDto;
import com.streisky.precoreal.dtos.PriceCalculationResponseDto;
import com.streisky.precoreal.services.interfaces.PriceCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/taxes")
@RequiredArgsConstructor
public class PriceCalculatorControllerImpl implements PriceCalculatorController {

    private final PriceCalculatorService priceCalculatorService;

    @Override
    @PostMapping("/calculate")
    public PriceCalculationResponseDto calculate(@RequestBody PriceCalculationRequestDto request) {
        return priceCalculatorService.calculate(request);
    }
}
