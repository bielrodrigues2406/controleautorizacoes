package br.edu.ifsp.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogAcao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String usuario; // username de quem fez a ação

    private String acao; // ex: "Solicitação criada", "Chave devolvida"

    private String entidade; // ex: "SolicitacaoChave", "Autorizacao"

    private Long referenciaId; // ID da entidade afetada

    private LocalDateTime 
     dataHora = LocalDateTime.now();
     
}
