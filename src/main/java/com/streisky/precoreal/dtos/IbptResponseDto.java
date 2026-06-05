package com.streisky.precoreal.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.streisky.precoreal.models.Ibpt;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class IbptResponseDto {

    private String ncm;
    private String uf;
    private String tipo;
    private String descricao;
    private BigDecimal aliquotaNacional;
    private BigDecimal aliquotaImportado;
    private BigDecimal aliquotaEstadual;
    private BigDecimal aliquotaMunicipal;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate vigenciaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate vigenciaFim;
    private String chave;
    private String versao;
    private String fonte;

    public static IbptResponseDto fromEntity(Ibpt ibpt) {
        return IbptResponseDto.builder()
                .ncm(ibpt.getId().getNcm())
                .uf(ibpt.getId().getUf())
                .tipo(ibpt.getTipo())
                .descricao(ibpt.getDescricao())
                .aliquotaNacional(ibpt.getAliquotaNacional())
                .aliquotaImportado(ibpt.getAliquotaImportado())
                .aliquotaEstadual(ibpt.getAliquotaEstadual())
                .aliquotaMunicipal(ibpt.getAliquotaMunicipal())
                .vigenciaInicio(ibpt.getVigenciaInicio())
                .vigenciaFim(ibpt.getVigenciaFim())
                .chave(ibpt.getChave())
                .versao(ibpt.getVersao())
                .fonte(ibpt.getFonte())
                .build();
    }
}
