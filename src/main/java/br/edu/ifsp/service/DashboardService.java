package br.edu.ifsp.service;

import br.edu.ifsp.dto.DashboardDTO;
import br.edu.ifsp.enums.StatusChave;
import br.edu.ifsp.repository.AmbienteRepository;
import br.edu.ifsp.repository.AutorizacaoRepository;
import br.edu.ifsp.repository.SolicitacaoChaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AutorizacaoRepository autorizacaoRepo;
    private final SolicitacaoChaveRepository solicitacaoRepo;
    private final AmbienteRepository ambienteRepo;

    public DashboardDTO gerarResumo() {
        long totalAutorizacoes = autorizacaoRepo.count();
        long chavesSolicitadas = solicitacaoRepo.countByStatus(StatusChave.SOLICITADA);
        long chavesEntregues = solicitacaoRepo.countByStatus(StatusChave.ENTREGUE);
        long devolucoesPendentes = solicitacaoRepo.countByStatus(StatusChave.ENTREGUE);
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
