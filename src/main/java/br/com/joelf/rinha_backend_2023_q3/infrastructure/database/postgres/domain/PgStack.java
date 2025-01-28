package br.com.joelf.rinha_backend_2023_q3.infrastructure.database.postgres.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PgStack {
    private UUID pessoa_id;
    private String stackItem;
}
