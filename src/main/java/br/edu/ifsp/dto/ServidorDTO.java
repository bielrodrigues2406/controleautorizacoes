package br.edu.ifsp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorDTO {
    @NotBlank
    private String nome;

    @NotBlank
    private String prontuario;

    private String email;
}

