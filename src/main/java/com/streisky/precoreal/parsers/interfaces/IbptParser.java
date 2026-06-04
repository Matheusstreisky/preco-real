package com.streisky.precoreal.parsers.interfaces;

import com.streisky.precoreal.models.Ibpt;

import java.util.List;

public interface IbptParser {

    /**
     * Faz o parse do conteúdo bruto e retorna os registros IBPT da UF.
     *
     * @param csv conteúdo bruto (CSV ou JSON) da tabela IBPT
     * @param uf  sigla do estado (ex: SP, RJ)
     * @return lista de entidades {@link com.streisky.precoreal.models.Ibpt} extraídas
     */
    List<Ibpt> parse(String csv, String uf);
}
