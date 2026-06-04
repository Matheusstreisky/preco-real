package com.streisky.precoreal.repositories.custom;

import com.streisky.precoreal.models.Ibpt;

import java.util.List;

public interface IbptRepositoryCustom {

    /**
     * Insere ou atualiza os registros IBPT da UF em lotes de 500 e remove os obsoletos.
     * Registros não presentes em {@code entries} são deletados da UF ao final do upsert.
     *
     * @param uf      sigla do estado (ex: SP, RJ)
     * @param entries lista de entidades a serem persistidas
     * @return número de registros processados
     */
    int upsertIbpt(String uf, List<Ibpt> entries);
}
