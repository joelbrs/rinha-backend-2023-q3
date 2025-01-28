package br.com.joelf.rinha_backend_2023_q3.infrastructure.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.zaxxer.hikari.HikariDataSource;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.mappers.PessoaRowMapper;

@Configuration
public class JDBCConfig {

    private final String jdbcDriver;
    private final String jdbcUrl;
    private final String jdbcUsername;
    private final String jdbcPassword;
    private final Integer maximumPoolSize;
    private final Integer minimumIdle;
    private final Integer connectionTimeout;

    public JDBCConfig(
            @Value("${spring.datasource.driver-class-name}") String jdbcDriver,
            @Value("${spring.datasource.url}") String jdbcUrl,
            @Value("${spring.datasource.username}") String jdbcUsername,
            @Value("${spring.datasource.password}") String jdbcPassword,
            @Value("${spring.datasource.hikari.maximum-pool-size}") Integer maximumPoolSize,
            @Value("${spring.datasource.hikari.minimum-idle}") Integer minimumIdle,
            @Value("${spring.datasource.hikari.connection-timeout}") Integer connectionTimeout
    ) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
        this.maximumPoolSize = maximumPoolSize;
        this.minimumIdle = minimumIdle;
        this.connectionTimeout = connectionTimeout;
    }

    @Bean
    public DataSource postgresDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setMaximumPoolSize(maximumPoolSize);  
        dataSource.setMinimumIdle(minimumIdle);
        dataSource.setConnectionTimeout(connectionTimeout);

        return dataSource;
    }

    @Bean
    public RowMapper<Pessoa> pessoaRowMapper() {
        return new PessoaRowMapper();
    }
}
