package com.streisky.precoreal.services.interfaces;

import com.streisky.precoreal.models.Ibpt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IbptService {

    /**
     * Retorna os registros IBPT de uma UF de forma paginada.
     *
     * @param uf       sigla do estado (ex: SP, RJ)
     * @param pageable configuração de paginação e ordenação
     * @return página de registros IBPT da UF informada
     */
    Page<Ibpt> findAllByUf(String uf, Pageable pageable);

    /**
     * Busca um registro IBPT pelo código NCM e UF.
     *
     * @param ncm código NCM de 8 dígitos
     * @param uf  sigla do estado (ex: SP, RJ)
     * @return entidade IBPT correspondente
     * @throws java.util.NoSuchElementException se o NCM não for encontrado para a UF
     */
    Ibpt findByNcmAndUf(String ncm, String uf);

    /**
     * Persiste em lote os registros IBPT de uma UF, fazendo upsert e removendo obsoletos.
     *
     * @param uf      sigla do estado (ex: SP, RJ)
     * @param entries lista de entidades a serem salvas
     * @return número de registros processados
     */
    int saveIbpt(String uf, List<Ibpt> entries);
}
