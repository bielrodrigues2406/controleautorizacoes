package br.edu.ifsp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoChaveDTO {
    @NotNull
    private Long alunoId;

    @NotNull
    private Long ambienteId;
}
