package br.com.joelf.rinha_backend_2023_q3.infrastructure.database.postgres;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.transaction.annotation.Transactional;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.CacheRepository;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.PessoaRepository;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.exceptions.EntityNotFoundException;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.postgres.domain.PgStack;
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
                select * from tb_pessoas p 
                left join tb_pessoas_stack s on s.pessoa_id = p.id 
                where p.id = :id;
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);
        
        pessoa = 
            namedParameterJdbcTemplate.queryForObject(query, parameters, pessoaRowMapper);

        if (pessoa == null) {
            throw new EntityNotFoundException("Pessoa não encontrada");
        }
        cacheRepository.set(id.toString(), pessoa);
        return pessoa;
    }

    @Override
    public List<Pessoa> getListPessoas(String searchTerm) {
        List<Pessoa> pessoas = cacheRepository.getList(searchTerm);

        if (pessoas != null && !pessoas.isEmpty()) {
            return pessoas;
        }

        String query = """
                select * from tb_pessoas p 
                left join tb_pessoas_stack s on s.pessoa_id = p.id 
                where :searchTerm is null or 
                (
                    lower(p.nome) like lower(:searchTerm) or 
                    lower(p.apelido) like lower(:searchTerm) or 
                    lower(s.stack_item) like lower(:searchTerm) or
                    lower(s.stack_item) like lower(:searchTerm)
                )
                limit 50;
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("searchTerm", searchTerm);

        pessoas = namedParameterJdbcTemplate.query(query, parameters, pessoaRowMapper);
        cacheRepository.setList(searchTerm, pessoas);

        return pessoas;
    }

    @Override
    @Transactional
    public UUID createPessoa(Pessoa pessoa) {
        pessoa.setId(UUID.randomUUID());

        String insertPessoaSql = "insert into tb_pessoas (id, nome, apelido, nascimento) values (?, ?, ?, ?)";
        jdbcTemplate.update(insertPessoaSql, pessoa.getId(), pessoa.getNome(), pessoa.getApelido(), pessoa.getNascimento());
        
        if (pessoa.getStack() != null && !pessoa.getStack().isEmpty()) {
            String insertStackSql = "insert into tb_pessoas_stack (pessoa_id, stack_item) values (:pessoa_id, :stackItem)";
            
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(
                pessoa.getStack().stream().map(stackItem -> new PgStack(pessoa.getId(), stackItem)
            ).toArray());
            namedParameterJdbcTemplate.batchUpdate(insertStackSql, batch);
        }
        
        cacheRepository.set(pessoa.getId().toString(), pessoa);
        return pessoa.getId();
    }

    @Override
    public Integer countPessoa() {
        String query = "select count(*) from tb_pessoas";
        return jdbcTemplate.queryForObject(query, Integer.class)  ; 
    }
}
