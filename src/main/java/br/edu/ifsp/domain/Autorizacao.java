package br.edu.ifsp.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import br.edu.ifsp.enums.DiasSemana;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Autorizacao {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Servidor servidor;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Ambiente ambiente;

    private String atividade; // TCC, Pesquisa, Extensão...

    @Enumerated(EnumType.STRING)
    private DiasSemana diaSemana;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    private LocalDate dataInicio;
    private LocalDate dataFim;
}
