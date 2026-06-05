package com.streisky.precoreal.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PriceCalculationResponseDto {
    private BigDecimal originalPrice;
    private BigDecimal priceWithoutTax;
    /** Valor monetário do imposto cobrado (originalPrice - priceWithoutTax) */
    private BigDecimal taxValue;
    /** Soma de todas as alíquotas aplicadas: federal + estadual + municipal (%) */
    private BigDecimal totalTaxRate;
    private TaxBreakdown breakdown;

    @Data
    @Builder
    public static class TaxBreakdown {
        private BigDecimal federal;
        private BigDecimal estadual;
        private BigDecimal municipal;
    }
}
