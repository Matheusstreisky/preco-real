package com.streisky.precoreal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ibpt")
public class Ibpt {

    @EmbeddedId
    private IbptId id;

    private String tipo;

    @Column(length = 500)
    private String descricao;

    @Column(precision = 10, scale = 4)
    private BigDecimal aliquotaNacional;

    @Column(precision = 10, scale = 4)
    private BigDecimal aliquotaImportado;

    @Column(precision = 10, scale = 4)
    private BigDecimal aliquotaEstadual;

    @Column(precision = 10, scale = 4)
    private BigDecimal aliquotaMunicipal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate vigenciaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate vigenciaFim;

    private String chave;
    private String versao;
    private String fonte;
}
