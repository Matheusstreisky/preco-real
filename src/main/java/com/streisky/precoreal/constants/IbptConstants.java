package com.streisky.precoreal.constants;

public final class IbptConstants {

    private IbptConstants() {}

    public interface Csv {
        public static final int COLUNA_NCM                = 0;
        public static final int COLUNA_EXCECAO_TARIFARIA  = 1;
        public static final int COLUNA_TIPO               = 2;
        public static final int COLUNA_DESCRICAO          = 3;
        public static final int COLUNA_ALIQUOTA_NACIONAL  = 4;
        public static final int COLUNA_ALIQUOTA_IMPORTADO = 5;
        public static final int COLUNA_ALIQUOTA_ESTADUAL  = 6;
        public static final int COLUNA_ALIQUOTA_MUNICIPAL = 7;
        public static final int COLUNA_VIGENCIA_INICIO    = 8;
        public static final int COLUNA_VIGENCIA_FIM       = 9;
        public static final int COLUNA_CHAVE              = 10;
        public static final int COLUNA_VERSAO             = 11;
        public static final int COLUNA_FONTE              = 12;
    }
}
