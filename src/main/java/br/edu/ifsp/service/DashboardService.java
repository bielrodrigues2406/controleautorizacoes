package br.edu.ifsp.service;
import br.edu.ifsp.dto.DashboardDTO;
import br.edu.ifsp.enums.StatusChave;
import br.edu.ifsp.repository.AmbienteRepository;
import br.edu.ifsp.repository.AutorizacaoRepository;
import br.edu.ifsp.repository.SolicitacaoChaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AutorizacaoRepository autorizacaoRepo;
    private final SolicitacaoChaveRepository solicitacaoRepo;
    private final AmbienteRepository ambienteRepo;

    public DashboardDTO gerarResumoFiltrado(LocalDate inicio, LocalDate fim, StatusChave status) {
        long totalAutorizacoes = autorizacaoRepo.countByDataInicioBetween(inicio, fim);

        long chavesSolicitadas = solicitacaoRepo.countByStatusAndDataSolicitacaoBetween(
                StatusChave.SOLICITADA, inicio.atStartOfDay(), fim.atTime(23, 59));

        long chavesEntregues = solicitacaoRepo.countByStatusAndDataEntregaBetween(
                StatusChave.ENTREGUE, inicio.atStartOfDay(), fim.atTime(23, 59));

        long devolucoesPendentes = solicitacaoRepo.countByStatusAndDataDevolucaoIsNull();

        long ambientesDisponiveis = ambienteRepo.countByDisponivelTrue();

        return new DashboardDTO(
                totalAutorizacoes,
                chavesSolicitadas,
                chavesEntregues,
                devolucoesPendentes,
                ambientesDisponiveis
        );
    }
}
