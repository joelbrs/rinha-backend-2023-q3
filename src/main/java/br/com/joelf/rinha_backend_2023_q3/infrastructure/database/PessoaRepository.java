package br.com.joelf.rinha_backend_2023_q3.infrastructure.database;

import java.util.List;
import java.util.UUID;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;

public interface PessoaRepository {
    Pessoa getById(UUID id);
    List<Pessoa> getListPessoas(String searchTerm);
    UUID createPessoa(Pessoa pessoa);
    Integer countPessoa();
}
