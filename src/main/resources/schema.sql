CREATE TABLE IF NOT EXISTS tb_pessoas (
    id UUID DEFAULT gen_random_uuid(),
    apelido  VARCHAR(255) CONSTRAINT id_pk PRIMARY KEY,
    nome  VARCHAR(255),
    nascimento DATE,
    stack VARCHAR(1024),
    busca_trgm TEXT GENERATED ALWAYS AS (
        LOWER(nome || apelido || stack)
    ) STORED
);

CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_pessoas_busca_trgm ON tb_pessoas USING gist (busca_trgm gist_trgm_ops(SIGLEN=64));