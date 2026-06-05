package com.streisky.precoreal.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceCalculationRequestDto {
    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    @Size(max = 10)
    private String ncm;

    @NotBlank
    @Size(min = 2, max = 2)
    private String uf;

    /** true = usa alíquota de importado; false/null = usa alíquota nacional */
    private Boolean imported;
}
