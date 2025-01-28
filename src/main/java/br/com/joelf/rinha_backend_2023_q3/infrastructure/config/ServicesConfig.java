package br.com.joelf.rinha_backend_2023_q3.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.joelf.rinha_backend_2023_q3.application.services.PessoaServiceImpl;
import br.com.joelf.rinha_backend_2023_q3.domain.services.PessoaService;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.PessoaRepository;

@Configuration
public class ServicesConfig {

    @Bean
    public PessoaService pessoaService(
        PessoaRepository pessoaRepository
    ) {
        return new PessoaServiceImpl(pessoaRepository);
    }
}