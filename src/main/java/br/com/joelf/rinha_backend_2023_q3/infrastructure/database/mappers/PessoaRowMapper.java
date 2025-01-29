package br.com.joelf.rinha_backend_2023_q3.infrastructure.database.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;

public class PessoaRowMapper implements RowMapper<Pessoa> {

    @Override
    public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Pessoa.builder()
                .id(UUID.fromString(rs.getString("id")))
                .nome(rs.getString("nome"))
                .apelido(rs.getString("apelido"))
                .nascimento(rs.getDate("nascimento"))
                .stack(getStacks(rs.getString("stack")))
                .build();
    }

    private List<String> getStacks(String stack) {
        if (stack != null && !stack.isEmpty()) {
            return Arrays.asList(stack.split(","));
        }
        return Collections.emptyList();
    }
}
