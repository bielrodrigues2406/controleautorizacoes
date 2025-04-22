package br.edu.ifsp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerfilDTO {
    private String nome;
    private String email;
    private String tipo; // ALUNO, SERVIDOR, CAE
    private String curso; // se for aluno
    private String prontuario;
}