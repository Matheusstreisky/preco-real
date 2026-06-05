package com.streisky.precoreal.services;

import com.streisky.precoreal.dtos.PriceCalculationResponseDto;
import com.streisky.precoreal.factories.IbptFactory;
import com.streisky.precoreal.factories.PriceCalculationFactory;
import com.streisky.precoreal.services.interfaces.IbptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceCalculatorServiceImplTest {

    @Mock
    private IbptService ibptService;

    @InjectMocks
    private PriceCalculatorServiceImpl service;

    private final IbptFactory ibptFactory = new IbptFactory();
    private final PriceCalculationFactory priceCalculationFactory = new PriceCalculationFactory();

    @Test
    void calculate_withNationalProduct_usesAliquotaNacional() {
        when(ibptService.findByNcmAndUf("12345678", "SP")).thenReturn(ibptFactory.build());

        PriceCalculationResponseDto result = service.calculate(priceCalculationFactory.buildRequest(false));

        assertThat(result.getTotalTaxRate()).isEqualByComparingTo("17.00"); // 10 + 5 + 2
        assertThat(result.getBreakdown().getFederal()).isEqualByComparingTo("10.00");
    }

    @Test
    void calculate_withImportedProduct_usesAliquotaImportado() {
        when(ibptService.findByNcmAndUf("12345678", "SP")).thenReturn(ibptFactory.build());

        PriceCalculationResponseDto result = service.calculate(priceCalculationFactory.buildRequest(true));

        assertThat(result.getTotalTaxRate()).isEqualByComparingTo("27.00"); // 20 + 5 + 2
        assertThat(result.getBreakdown().getFederal()).isEqualByComparingTo("20.00");
    }

    @Test
    void calculate_originalPriceEqualsWithoutTaxPlusTaxValue() {
        when(ibptService.findByNcmAndUf("12345678", "SP")).thenReturn(ibptFactory.build());

        PriceCalculationResponseDto result = service.calculate(priceCalculationFactory.buildRequest());

        BigDecimal sum = result.getPriceWithoutTax().add(result.getTaxValue());
        assertThat(sum).isEqualByComparingTo(result.getOriginalPrice());
    }

    @Test
    void calculate_priceWithoutTaxIsLessThanOriginalPrice() {
        when(ibptService.findByNcmAndUf("12345678", "SP")).thenReturn(ibptFactory.build());

        PriceCalculationResponseDto result = service.calculate(priceCalculationFactory.buildRequest());

        assertThat(result.getPriceWithoutTax()).isLessThan(result.getOriginalPrice());
        assertThat(result.getTaxValue()).isPositive();
    }
}
