package br.edu.ifsp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardDTO {
    private long totalAutorizacoes;
    private long chavesSolicitadas;
    private long chavesEntregues;
    private long devolucoesPendentes;
    private long ambientesDisponiveis;
}
