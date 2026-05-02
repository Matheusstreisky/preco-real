package com.streisky.precoreal.parsers;

import com.streisky.precoreal.constants.IbptConstants;
import com.streisky.precoreal.models.Ibpt;
import com.streisky.precoreal.models.IbptId;
import com.streisky.precoreal.parsers.interfaces.IbptParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CsvIbptParser implements IbptParser {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * @param csv conteúdo do arquivo CSV da tabela IBPT (ISO-8859-1 já decodificado)
     * @param uf  código do estado (ex: SP, RJ)
     */
    public List<Ibpt> parse(String csv, String uf) {
        String[] lines = csv.split("\n");
        List<Ibpt> entries = new ArrayList<>(lines.length);
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            try {
                entries.add(parseLine(line, uf.toUpperCase()));
            } catch (Exception e) {
                log.warn("Linha {} da UF {} ignorada: {}", i, uf, e.getMessage());
            }
        }
        return entries;
    }

    private Ibpt parseLine(String line, String uf) {
        String[] p = line.split(";", -1);
        Ibpt entry = new Ibpt();
        entry.setId(new IbptId(p[IbptConstants.Csv.COLUNA_NCM].trim(), uf));
        entry.setTipo(p[IbptConstants.Csv.COLUNA_TIPO].trim());
        entry.setDescricao(p[IbptConstants.Csv.COLUNA_DESCRICAO].trim());
        entry.setAliquotaNacional(decimal(p[IbptConstants.Csv.COLUNA_ALIQUOTA_NACIONAL]));
        entry.setAliquotaImportado(decimal(p[IbptConstants.Csv.COLUNA_ALIQUOTA_IMPORTADO]));
        entry.setAliquotaEstadual(decimal(p[IbptConstants.Csv.COLUNA_ALIQUOTA_ESTADUAL]));
        entry.setAliquotaMunicipal(decimal(p[IbptConstants.Csv.COLUNA_ALIQUOTA_MUNICIPAL]));
        entry.setVigenciaInicio(date(p[IbptConstants.Csv.COLUNA_VIGENCIA_INICIO]));
        entry.setVigenciaFim(date(p[IbptConstants.Csv.COLUNA_VIGENCIA_FIM]));
        entry.setChave(p[IbptConstants.Csv.COLUNA_CHAVE].trim());
        entry.setVersao(p[IbptConstants.Csv.COLUNA_VERSAO].trim());
        entry.setFonte(p.length > IbptConstants.Csv.COLUNA_FONTE ? p[IbptConstants.Csv.COLUNA_FONTE].trim() : null);
        return entry;
    }

    private BigDecimal decimal(String s) {
        return new BigDecimal(s.trim().replace(",", "."));
    }

    private LocalDate date(String s) {
        try {
            return LocalDate.parse(s.trim(), DATE_FMT);
        } catch (Exception e) {
            return null;
        }
    }
}
