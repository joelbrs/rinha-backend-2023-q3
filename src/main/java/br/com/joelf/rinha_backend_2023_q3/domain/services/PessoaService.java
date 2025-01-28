package br.com.joelf.rinha_backend_2023_q3.domain.services;

import java.util.List;
import java.util.UUID;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;

public interface PessoaService {
    UUID createPessoa(Pessoa pessoa);
    Integer countPessoa();
    List<Pessoa> getListPessoas(String searchTerm);
    Pessoa getById(UUID id);
}
