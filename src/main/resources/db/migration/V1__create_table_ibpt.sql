CREATE TABLE IF NOT EXISTS ibpt (
    ncm                  VARCHAR(10)    NOT NULL,
    uf                   VARCHAR(2)     NOT NULL,
    tipo                 VARCHAR(50),
    descricao            VARCHAR(500),
    aliquota_nacional    DECIMAL(10, 4),
    aliquota_importado   DECIMAL(10, 4),
    aliquota_estadual    DECIMAL(10, 4),
    aliquota_municipal   DECIMAL(10, 4),
    vigencia_inicio      DATE,
    vigencia_fim         DATE,
    chave                VARCHAR(50),
    versao               VARCHAR(20),
    fonte                VARCHAR(100),
    CONSTRAINT pk_ibpt PRIMARY KEY (ncm, uf)
);
