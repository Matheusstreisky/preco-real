package com.streisky.precoreal.factories;

import com.streisky.precoreal.dtos.PriceCalculationRequestDto;
import com.streisky.precoreal.dtos.PriceCalculationResponseDto;

import java.math.BigDecimal;

public class PriceCalculationFactory {

    public PriceCalculationRequestDto buildRequest() {
        return buildRequest(false);
    }

    public PriceCalculationRequestDto buildRequest(boolean imported) {
        PriceCalculationRequestDto request = new PriceCalculationRequestDto();
        request.setNcm("12345678");
        request.setUf("SP");
        request.setPrice(new BigDecimal("100.00"));
        request.setImported(imported);
        return request;
    }

    public PriceCalculationResponseDto buildResponse() {
        return PriceCalculationResponseDto.builder()
                .originalPrice(new BigDecimal("100.00"))
                .priceWithoutTax(new BigDecimal("85.47"))
                .taxValue(new BigDecimal("14.53"))
                .totalTaxRate(new BigDecimal("17.00"))
                .build();
    }
}
