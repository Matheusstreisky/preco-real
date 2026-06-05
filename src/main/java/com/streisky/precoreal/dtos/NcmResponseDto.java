package com.streisky.precoreal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NcmResponseDto {
    private String ncm;
    private String descricao;
    private String confianca;
}
