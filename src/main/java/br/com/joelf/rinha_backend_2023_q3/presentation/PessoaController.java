package br.com.joelf.rinha_backend_2023_q3.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.joelf.rinha_backend_2023_q3.domain.entities.Pessoa;
import br.com.joelf.rinha_backend_2023_q3.domain.services.PessoaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PessoaController {
    
    private final PessoaService pessoaService;

    private static final String PREFIXO_ROTA_PESSOA = "/pessoas";

    @PostMapping(PREFIXO_ROTA_PESSOA)
    public ResponseEntity<Void> createPessoa(@RequestBody @Valid Pessoa pessoa) {
        String HEADER_LOCATION = "Location";

        UUID id = pessoaService.createPessoa(pessoa);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HEADER_LOCATION, PREFIXO_ROTA_PESSOA + "/" + id)
                .build();
    }

    @GetMapping(PREFIXO_ROTA_PESSOA + "/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(pessoaService.getById(id));
    }

    @GetMapping(PREFIXO_ROTA_PESSOA)
    public ResponseEntity<List<Pessoa>> getAllPessoas(
            @RequestParam(value = "t") String searchTerm
    ) {
        return ResponseEntity.ok().body(pessoaService.getListPessoas(searchTerm));
    }

    @GetMapping("/contagem-pessoas")
    public ResponseEntity<Integer> getContagemPessoas() {
        return ResponseEntity.ok().body(pessoaService.countPessoa());
    }
}
