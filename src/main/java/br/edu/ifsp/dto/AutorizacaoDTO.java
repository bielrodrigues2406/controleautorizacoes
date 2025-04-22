package br.edu.ifsp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ifsp.enums.DiasSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacaoDTO {
    @NotNull
    private Long servidorId;

    @NotNull
    private Long alunoId;

    @NotNull
    private Long ambienteId;

    @NotBlank
    private String atividade;

    @NotNull
    private DiasSemana diaSemana;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFim;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;
}
