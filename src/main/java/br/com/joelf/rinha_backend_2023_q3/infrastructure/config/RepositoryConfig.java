package br.com.joelf.rinha_backend_2023_q3.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.CacheRepository;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.PessoaRepository;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.postgres.PessoaRepositoryImpl;

@Configuration
public class RepositoryConfig {
    
    @Bean
    public PessoaRepository pessoaRepository(
        JdbcTemplate jdbcTemplate,
        NamedParameterJdbcTemplate namedParameterJdbcTemplate,
        CacheRepository<String, Pessoa> cacheRepository,
        RowMapper<Pessoa> pessoaRowMapper
    ) {
        return new PessoaRepositoryImpl(jdbcTemplate, namedParameterJdbcTemplate, cacheRepository, pessoaRowMapper);
    }
}
