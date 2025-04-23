package br.edu.ifsp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServidorDTO extends UsuarioComLoginDTO {
    // Nenhum campo adicional por enquanto
}
