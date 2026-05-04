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
        entry.setId(new IbptId(p[IbptConstants.CsvColumn.NCM].trim(), uf));
        entry.setTipo(p[IbptConstants.CsvColumn.TIPO].trim());
        entry.setDescricao(p[IbptConstants.CsvColumn.DESCRICAO].trim());
        entry.setAliquotaNacional(toDecimal(p[IbptConstants.CsvColumn.ALIQUOTA_NACIONAL]));
        entry.setAliquotaImportado(toDecimal(p[IbptConstants.CsvColumn.ALIQUOTA_IMPORTADO]));
        entry.setAliquotaEstadual(toDecimal(p[IbptConstants.CsvColumn.ALIQUOTA_ESTADUAL]));
        entry.setAliquotaMunicipal(toDecimal(p[IbptConstants.CsvColumn.ALIQUOTA_MUNICIPAL]));
        entry.setVigenciaInicio(parseDate(p[IbptConstants.CsvColumn.VIGENCIA_INICIO]));
        entry.setVigenciaFim(parseDate(p[IbptConstants.CsvColumn.VIGENCIA_FIM]));
        entry.setChave(p[IbptConstants.CsvColumn.CHAVE].trim());
        entry.setVersao(p[IbptConstants.CsvColumn.VERSAO].trim());
        entry.setFonte(p.length > IbptConstants.CsvColumn.FONTE ? p[IbptConstants.CsvColumn.FONTE].trim() : null);
        return entry;
    }

    private BigDecimal toDecimal(String s) {
        return new BigDecimal(s.trim().replace(",", "."));
    }

    private LocalDate parseDate(String s) {
        try {
            return LocalDate.parse(s.trim(), DATE_FMT);
        } catch (Exception e) {
            return null;
        }
    }
}
