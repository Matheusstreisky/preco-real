package com.streisky.precoreal.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PriceCalculationResponseDto {
    private BigDecimal originalPrice;
    private BigDecimal priceWithoutTax;
    private BigDecimal taxAmount;
    /** Soma de todas as alíquotas aplicadas (%) */
    private BigDecimal totalRate;
    private TaxBreakdown breakdown;

    @Data
    @Builder
    public static class TaxBreakdown {
        private BigDecimal federal;
        private BigDecimal state;
        private BigDecimal municipal;
    }
}
