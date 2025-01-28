package br.com.joelf.rinha_backend_2023_q3.application.services;

import java.util.List;
import java.util.UUID;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;
import br.com.joelf.rinha_backend_2023_q3.domain.services.PessoaService;
import br.com.joelf.rinha_backend_2023_q3.infrastructure.database.PessoaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    @Override
    public UUID createPessoa(Pessoa pessoa) {
        return pessoaRepository.createPessoa(pessoa);
    }

    @Override
    public Integer countPessoa() {
        return pessoaRepository.countPessoa();
    }

    @Override
    public List<Pessoa> getListPessoas(String searchTerm) {
        return pessoaRepository.getListPessoas(searchTerm);
    }

    @Override
    public Pessoa getById(UUID id) {
        return pessoaRepository.getById(id);
    }
}
