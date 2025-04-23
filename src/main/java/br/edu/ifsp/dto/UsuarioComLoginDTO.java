package br.edu.ifsp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioComLoginDTO {
    private String nome;
    private String prontuario;
    private String email;
    private String username;
    private String password;
}
