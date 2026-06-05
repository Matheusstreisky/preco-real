package com.streisky.precoreal.parsers;

import com.streisky.precoreal.models.Ibpt;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvIbptParserTest {

    private final CsvIbptParser parser = new CsvIbptParser();

    // ncm;excecao;tipo;descricao;nacional;importado;estadual;municipal;vigInicio;vigFim;chave;versao;fonte
    private static final String VALID_LINE =
            "12345678;0;2;Notebook;12,50;20,00;5,00;2,00;01/01/2024;31/12/2024;CHAVE123;1.0;IBPT";

    @Test
    void parse_validCsv_returnsEntries() {
        String csv = "header\n" + VALID_LINE;

        List<Ibpt> result = parser.parse(csv, "SP");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId().getNcm()).isEqualTo("12345678");
        assertThat(result.get(0).getId().getUf()).isEqualTo("SP");
        assertThat(result.get(0).getTipo()).isEqualTo("2");
        assertThat(result.get(0).getDescricao()).isEqualTo("Notebook");
        assertThat(result.get(0).getAliquotaNacional()).isEqualByComparingTo("12.50");
        assertThat(result.get(0).getAliquotaImportado()).isEqualByComparingTo("20.00");
        assertThat(result.get(0).getAliquotaEstadual()).isEqualByComparingTo("5.00");
        assertThat(result.get(0).getAliquotaMunicipal()).isEqualByComparingTo("2.00");
        assertThat(result.get(0).getFonte()).isEqualTo("IBPT");
    }

    @Test
    void parse_ufNormalized_toUpperCase() {
        String csv = "header\n" + VALID_LINE;

        List<Ibpt> result = parser.parse(csv, "sp");

        assertThat(result.get(0).getId().getUf()).isEqualTo("SP");
    }

    @Test
    void parse_withInvalidLine_skipsAndContinues() {
        String csv = "header\n" + VALID_LINE + "\nINVALID_LINE";

        List<Ibpt> result = parser.parse(csv, "SP");

        assertThat(result).hasSize(1);
    }

    @Test
    void parse_withOnlyHeader_returnsEmptyList() {
        List<Ibpt> result = parser.parse("header", "SP");

        assertThat(result).isEmpty();
    }

    @Test
    void parse_withBlankLines_ignoresThem() {
        String csv = "header\n\n" + VALID_LINE + "\n\n";

        List<Ibpt> result = parser.parse(csv, "SP");

        assertThat(result).hasSize(1);
    }

    @Test
    void parse_multipleValidLines_returnsAllEntries() {
        String secondLine = VALID_LINE.replace("12345678", "87654321");
        String csv = "header\n" + VALID_LINE + "\n" + secondLine;

        List<Ibpt> result = parser.parse(csv, "SP");

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId().getNcm()).isEqualTo("12345678");
        assertThat(result.get(1).getId().getNcm()).isEqualTo("87654321");
    }

    @Test
    void parse_lineWithoutFonteColumn_setsFonteAsNull() {
        String lineWithoutFonte = "12345678;0;2;Notebook;12,50;20,00;5,00;2,00;01/01/2024;31/12/2024;CHAVE123;1.0";
        String csv = "header\n" + lineWithoutFonte;

        List<Ibpt> result = parser.parse(csv, "SP");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFonte()).isNull();
    }

    @Test
    void parse_lineWithInvalidDate_setsVigenciaInicioAsNull() {
        String lineWithBadDate = "12345678;0;2;Notebook;12,50;20,00;5,00;2,00;data-invalida;31/12/2024;CHAVE123;1.0;IBPT";
        String csv = "header\n" + lineWithBadDate;

        List<Ibpt> result = parser.parse(csv, "SP");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getVigenciaInicio()).isNull();
        assertThat(result.get(0).getVigenciaFim()).isNotNull();
    }
}
