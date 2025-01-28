package br.com.joelf.rinha_backend_2023_q3.infrastructure.database.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;

public class PessoaRowMapper implements RowMapper<Pessoa> {

    @Override
    public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pessoa.PessoaBuilder pessoa = Pessoa.builder()
                .id(UUID.fromString(rs.getString("id")))
                .nome(rs.getString("nome"))
                .apelido(rs.getString("apelido"))
                .nascimento(rs.getDate("nascimento"));

        List<String> stack = new ArrayList<>();
        do {
            String stackItem = rs.getString("stack_item");
            if (stackItem != null) {
                stack.add(stackItem);
            }
        } while (rs.next());

        return pessoa.stack(stack).build();
    }
}
