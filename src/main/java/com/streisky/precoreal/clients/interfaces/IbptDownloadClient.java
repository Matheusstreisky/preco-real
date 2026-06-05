package com.streisky.precoreal.clients.interfaces;

public interface IbptDownloadClient {

    /**
     * Baixa a tabela IBPT para a UF informada.
     *
     * @param uf sigla do estado (ex: SP, RJ)
     * @return conteúdo bruto da tabela (JSON ou CSV)
     */
    String download(String uf);
}
