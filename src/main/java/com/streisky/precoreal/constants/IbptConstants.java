package com.streisky.precoreal.constants;

public final class IbptConstants {

    private IbptConstants() {}

    public interface CsvColumn {
        int NCM                = 0;
        int EXCECAO_TARIFARIA  = 1;
        int TIPO               = 2;
        int DESCRICAO          = 3;
        int ALIQUOTA_NACIONAL  = 4;
        int ALIQUOTA_IMPORTADO = 5;
        int ALIQUOTA_ESTADUAL  = 6;
        int ALIQUOTA_MUNICIPAL = 7;
        int VIGENCIA_INICIO    = 8;
        int VIGENCIA_FIM       = 9;
        int CHAVE              = 10;
        int VERSAO             = 11;
        int FONTE              = 12;
    }
}
