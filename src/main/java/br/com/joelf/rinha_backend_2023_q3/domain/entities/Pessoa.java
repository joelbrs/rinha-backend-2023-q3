package br.com.joelf.rinha_backend_2023_q3.domain.entities;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.joelf.rinha_backend_2023_q3.application.commons.ValidationConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    private UUID id;

    @NotNull    
    @Pattern(regexp = ValidationConstants.REGEX_JUST_LETTERS_STRING)
    private String nome;

    @NotNull
    private String apelido;
    private List<@Pattern(regexp = ValidationConstants.REGEX_JUST_LETTERS_STRING) String> stack;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ValidationConstants.DATE_PATTERN)
    private Date nascimento;
}
