package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.dtos.SyncResultDto;

public interface IbptSyncService {

    /**
     * Baixa e sincroniza a tabela IBPT de todos os estados configurados.
     *
     * @return resultado com totais de UFs processadas, erros e registros sincronizados
     */
    SyncResultDto syncAll();

    /**
     * Baixa e sincroniza a tabela IBPT de uma UF específica.
     *
     * @param uf sigla do estado (ex: SP, RJ)
     * @return número de registros sincronizados
     */
    int syncByUf(String uf);

    /**
     * Sincroniza a tabela IBPT de uma UF a partir do conteúdo bruto fornecido.
     *
     * @param uf  sigla do estado (ex: SP, RJ)
     * @param csv conteúdo bruto (CSV ou JSON) da tabela IBPT
     * @return número de registros sincronizados
     */
    int syncByUfAndCsv(String uf, String csv);
}
