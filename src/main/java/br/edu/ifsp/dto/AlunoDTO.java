package br.edu.ifsp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AlunoDTO extends UsuarioComLoginDTO {
    private String curso;
}
