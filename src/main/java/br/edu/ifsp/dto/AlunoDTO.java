package br.edu.ifsp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {
    private String nome;
    private String prontuario;
    private String curso;
    private String email;
    private String usuarioUsername; // para buscar o login relacionado
}