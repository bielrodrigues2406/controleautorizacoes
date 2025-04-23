package br.edu.ifsp.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TentativaLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int tentativas;

    private LocalDateTime ultimoErro;

    private boolean bloqueado;

    private LocalDateTime desbloqueioApos; // opcional: data de liberação automática
}
