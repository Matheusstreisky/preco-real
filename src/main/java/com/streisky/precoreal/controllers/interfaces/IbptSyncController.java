package com.streisky.precoreal.controllers.interfaces;

import com.streisky.precoreal.dtos.SyncResultDto;
import com.streisky.precoreal.models.Ibpt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface IbptSyncController {

    /**
     * Retorna todos os registros IBPT de uma UF.
     *
     * @param uf sigla do estado (ex: SP, RJ)
     * @return lista de registros IBPT da UF informada
     */
    List<Ibpt> findAllByUf(@RequestParam String uf);

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
     * @return mapa com a UF e a quantidade de registros sincronizados
     */
    Map<String, Object> syncByUf(@PathVariable String uf);

    /**
     * Sincroniza a tabela IBPT de uma UF a partir de CSV enviado no corpo da requisição.
     *
     * @param uf  sigla do estado (ex: SP, RJ)
     * @param csv conteúdo CSV em texto plano
     * @return mapa com a UF e a quantidade de registros sincronizados
     */
    Map<String, Object> syncByUfAndCsv(@PathVariable String uf, @RequestBody String csv);
}
