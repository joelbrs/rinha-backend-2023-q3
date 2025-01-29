package br.com.joelf.rinha_backend_2023_q3.infrastructure.database.postgres;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.CacheRepository;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.PessoaRepository;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PessoaRepositoryImpl implements PessoaRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final CacheRepository<String, Pessoa> cacheRepository;

    private final RowMapper<Pessoa> pessoaRowMapper;

    @Override
    public Pessoa getById(UUID id) {
        Pessoa pessoa = cacheRepository.get(id.toString());

        if (pessoa != null) {
            return pessoa;
        }

        String query = """
                select id, apelido, nome, nascimento, stack from tb_pessoas p
                where p.id = :id;
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);
        
        try {
            pessoa = 
                namedParameterJdbcTemplate.queryForObject(query, parameters, pessoaRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Pessoa não encontrada");
        }
        return pessoa;
    }

    @Override
    public List<Pessoa> getListPessoas(String searchTerm) {
        String query = """
                select id, apelido, nome, nascimento, stack from tb_pessoas p 
                where lower(p.busca_trgm) like '%' || lower(:searchTerm) || '%'
                limit 50;
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("searchTerm", searchTerm);
        return namedParameterJdbcTemplate.query(query, parameters, pessoaRowMapper);
    }

    @Override
    public UUID createPessoa(Pessoa pessoa) {
        Pessoa pessoaExistente = cacheRepository.get(pessoa.getApelido());

        if (pessoaExistente != null) {
            throw new DuplicateKeyException("Apelido já cadastrado");
        }

        pessoa.setId(UUID.randomUUID());
        String stack = null;

        if (pessoa.getStack() != null && !pessoa.getStack().isEmpty()) {
            stack = pessoa.getStack().stream().collect(Collectors.joining(","));
        }

        String insertPessoaSql = "insert into tb_pessoas (id, nome, apelido, nascimento, stack) values (?, ?, ?, ?, ?) on conflict (apelido) do nothing;";
        jdbcTemplate.update(insertPessoaSql, pessoa.getId(), pessoa.getNome(), pessoa.getApelido(), pessoa.getNascimento(), stack);
        
        cacheRepository.set(pessoa.getId().toString(), pessoa);
        cacheRepository.set(pessoa.getApelido(), new Pessoa());
        return pessoa.getId();
    }

    @Override
    public Integer countPessoa() {
        String query = "select count(*) from tb_pessoas;";
        return jdbcTemplate.queryForObject(query, Integer.class)  ; 
    }
}
