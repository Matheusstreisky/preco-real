package com.streisky.precoreal.parsers;

import com.streisky.precoreal.models.Ibpt;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonIbptParserTest {

    private final JsonIbptParser parser = new JsonIbptParser();

    private static final String ENTRY_JSON = """
            {
              "codigo": "12345678",
              "tipo": 2,
              "descricao": "Notebook",
              "nacionalfederal": "12.5",
              "importadosfederal": "20.0",
              "estadual": "5.0",
              "municipal": "2.0",
              "vigenciainicio": "2024-01-01",
              "vigenciafim": "2024-12-31",
              "versao": "1.0",
              "fonte": "IBPT",
              "uf": "SP"
            }
            """;

    @Test
    void parse_singleObject_returnsSingleEntry() {
        List<Ibpt> result = parser.parse(ENTRY_JSON, "SP");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId().getNcm()).isEqualTo("12345678");
        assertThat(result.get(0).getId().getUf()).isEqualTo("SP");
        assertThat(result.get(0).getDescricao()).isEqualTo("Notebook");
        assertThat(result.get(0).getAliquotaNacional()).isEqualByComparingTo("12.5");
        assertThat(result.get(0).getAliquotaEstadual()).isEqualByComparingTo("5.0");
    }

    @Test
    void parse_arrayJson_returnsAllEntries() {
        String secondEntry = ENTRY_JSON.replace("12345678", "87654321");
        String json = "[" + ENTRY_JSON + "," + secondEntry + "]";

        List<Ibpt> result = parser.parse(json, "SP");

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId().getNcm()).isEqualTo("12345678");
        assertThat(result.get(1).getId().getNcm()).isEqualTo("87654321");
    }

    @Test
    void parse_objectWithNcmArray_returnsEntries() {
        String json = """
                {"ncm": [%s]}
                """.formatted(ENTRY_JSON);

        List<Ibpt> result = parser.parse(json, "SP");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId().getNcm()).isEqualTo("12345678");
    }

    @Test
    void parse_ufFallbackToParam_whenNotInJson() {
        String json = """
                {"codigo":"12345678","tipo":2,"descricao":"Test",
                 "nacionalfederal":"10.0","importadosfederal":"20.0",
                 "estadual":"5.0","municipal":"2.0"}
                """;

        List<Ibpt> result = parser.parse(json, "RJ");

        assertThat(result.get(0).getId().getUf()).isEqualTo("RJ");
    }

    @Test
    void parse_invalidJson_throwsIllegalArgumentException() {
        assertThatThrownBy(() -> parser.parse("not valid json", "SP"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("SP");
    }

    @Test
    void parse_arrayWithInvalidEntry_skipsInvalidAndReturnsValid() {
        String entryWithInvalidDecimal = """
                {"codigo":"99999999","tipo":2,"descricao":"test",
                 "nacionalfederal":"INVALIDO","importadosfederal":"0",
                 "estadual":"0","municipal":"0","uf":"SP"}
                """;
        String json = "[" + ENTRY_JSON + ", " + entryWithInvalidDecimal + "]";

        List<Ibpt> result = parser.parse(json, "SP");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId().getNcm()).isEqualTo("12345678");
    }

    @Test
    void parse_entryWithInvalidDate_setsVigenciaAsNull() {
        String json = """
                {
                  "codigo": "12345678",
                  "tipo": 2,
                  "descricao": "Notebook",
                  "nacionalfederal": "12.5",
                  "importadosfederal": "20.0",
                  "estadual": "5.0",
                  "municipal": "2.0",
                  "vigenciainicio": "data-invalida",
                  "vigenciafim": "tambem-invalida",
                  "uf": "SP"
                }
                """;

        List<Ibpt> result = parser.parse(json, "SP");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getVigenciaInicio()).isNull();
        assertThat(result.get(0).getVigenciaFim()).isNull();
    }
}
