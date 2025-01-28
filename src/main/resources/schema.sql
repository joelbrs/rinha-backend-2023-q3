CREATE TABLE IF NOT EXISTS tb_pessoas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    apelido VARCHAR(255) NOT NULL UNIQUE,
    nascimento DATE,
    CONSTRAINT nome_apelido_unique UNIQUE (nome, apelido)
);

CREATE INDEX IF NOT EXISTS idx_nome ON tb_pessoas (nome);
CREATE INDEX IF NOT EXISTS idx_apelido ON tb_pessoas (apelido);
CREATE INDEX IF NOT EXISTS idx_nascimento ON tb_pessoas (nascimento);

CREATE TABLE IF NOT EXISTS tb_pessoas_stack (
    pessoa_id UUID NOT NULL,
    stack_item VARCHAR(255) NOT NULL,
    PRIMARY KEY (pessoa_id, stack_item),
    FOREIGN KEY (pessoa_id) REFERENCES tb_pessoas(id) ON DELETE CASCADE
);