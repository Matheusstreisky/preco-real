package com.streisky.precoreal.factories;

import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.models.IbptId;

import java.math.BigDecimal;

public class IbptFactory {

    public Ibpt build() {
        return build("12345678", "SP");
    }

    public Ibpt build(String ncm, String uf) {
        Ibpt ibpt = new Ibpt();
        ibpt.setId(new IbptId(ncm, uf));
        ibpt.setDescricao("Produto teste");
        ibpt.setAliquotaNacional(new BigDecimal("10.00"));
        ibpt.setAliquotaImportado(new BigDecimal("20.00"));
        ibpt.setAliquotaEstadual(new BigDecimal("5.00"));
        ibpt.setAliquotaMunicipal(new BigDecimal("2.00"));
        return ibpt;
    }
}
