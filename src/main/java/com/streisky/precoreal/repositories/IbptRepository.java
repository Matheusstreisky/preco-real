package com.streisky.precoreal.repositories;

import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.models.IbptId;
import com.streisky.precoreal.repositories.custom.IbptRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IbptRepository extends JpaRepository<Ibpt, IbptId>, IbptRepositoryCustom {

    /**
     * Busca um registro IBPT pelo código NCM e sigla da UF.
     *
     * @param ncm código NCM de 8 dígitos
     * @param uf  sigla do estado (ex: SP, RJ)
     * @return Optional contendo o registro se encontrado
     */
    @Query("SELECT e FROM Ibpt e WHERE e.id.ncm = :ncm AND e.id.uf = :uf")
    Optional<Ibpt> findByNcmAndUf(@Param("ncm") String ncm, @Param("uf") String uf);

}
