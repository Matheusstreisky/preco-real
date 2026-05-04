package com.streisky.precoreal.repositories.custom;

import com.streisky.precoreal.models.Ibpt;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IbptRepositoryCustomImpl implements IbptRepositoryCustom {

    private static final int BATCH_SIZE = 500;
    private static final String UPSERT_SQL = """
            INSERT INTO ibpt (ncm, uf, tipo, descricao, aliquota_nacional, aliquota_importado,
                aliquota_estadual, aliquota_municipal, vigencia_inicio, vigencia_fim, chave, versao, fonte)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (ncm, uf) DO UPDATE SET
                tipo               = EXCLUDED.tipo,
                descricao          = EXCLUDED.descricao,
                aliquota_nacional  = EXCLUDED.aliquota_nacional,
                aliquota_importado = EXCLUDED.aliquota_importado,
                aliquota_estadual  = EXCLUDED.aliquota_estadual,
                aliquota_municipal = EXCLUDED.aliquota_municipal,
                vigencia_inicio    = EXCLUDED.vigencia_inicio,
                vigencia_fim       = EXCLUDED.vigencia_fim,
                chave              = EXCLUDED.chave,
                versao             = EXCLUDED.versao,
                fonte              = EXCLUDED.fonte
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int upsertIbpt(String uf, List<Ibpt> entries) {
        upsertInBatches(entries);
        deleteStale(uf, entries);
        return entries.size();
    }

    private void upsertInBatches(List<Ibpt> entries) {
        for (int i = 0; i < entries.size(); i += BATCH_SIZE) {
            List<Ibpt> chunk = entries.subList(i, Math.min(i + BATCH_SIZE, entries.size()));
            jdbcTemplate.batchUpdate(UPSERT_SQL, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int j) throws SQLException {
                    Ibpt e = chunk.get(j);
                    List<Object> values = Arrays.asList(
                            e.getId().getNcm(),
                            e.getId().getUf(),
                            e.getTipo(),
                            e.getDescricao(),
                            e.getAliquotaNacional(),
                            e.getAliquotaImportado(),
                            e.getAliquotaEstadual(),
                            e.getAliquotaMunicipal(),
                            e.getVigenciaInicio(),
                            e.getVigenciaFim(),
                            e.getChave(),
                            e.getVersao(),
                            e.getFonte()
                    );
                    for (int i = 0; i < values.size(); i++) {
                        ps.setObject(i + 1, values.get(i));
                    }
                }

                @Override
                public int getBatchSize() {
                    return chunk.size();
                }
            });
        }
    }

    private void deleteStale(String uf, List<Ibpt> entries) {
        String[] ncms = entries.stream()
                .map(e -> e.getId().getNcm())
                .toArray(String[]::new);
        jdbcTemplate.update(conn -> {
            Array ncmArray = conn.createArrayOf("varchar", ncms);
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM ibpt WHERE uf = ? AND ncm != ALL(?)");
            ps.setString(1, uf.toUpperCase());
            ps.setArray(2, ncmArray);
            return ps;
        });
    }
}
