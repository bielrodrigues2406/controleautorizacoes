package br.edu.ifsp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmbienteDTO {
    @NotBlank
    private String nome;

    private String localizacao;
}
