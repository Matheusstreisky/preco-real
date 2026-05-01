package com.streisky.precoreal.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceCalculationRequestDto {
    private BigDecimal price;
    private String ncm;
    private String uf;
    /** true = usa alíquota de importado; false/null = usa alíquota nacional */
    private Boolean imported;
}
