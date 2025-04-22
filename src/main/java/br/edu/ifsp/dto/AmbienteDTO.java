package br.edu.ifsp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmbienteDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String localizacao;
}
