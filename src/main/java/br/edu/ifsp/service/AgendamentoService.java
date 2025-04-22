package br.edu.ifsp.service.agendamento;

import br.edu.ifsp.domain.Autorizacao;
import br.edu.ifsp.repository.AutorizacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgendamentoService {

    private final AutorizacaoRepository autorizacaoRepository;

    /**
     * Verifica todos os dias às 06:00 se há autorizações vencidas
     */
    @Scheduled(cron = "0 0 6 * * *") // 06:00 AM todos os dias
    public void verificarAutorizacoesVencidas() {
        LocalDate hoje = LocalDate.now();

        List<Autorizacao> vencidas = autorizacaoRepository.findByDataFimBefore(hoje);
        if (vencidas.isEmpty()) {
            log.info("[Agendamento] Nenhuma autorização vencida encontrada para {}", hoje);
        } else {
            log.warn("[Agendamento] Autorizações vencidas detectadas em {}: {}", hoje, vencidas.size());
            vencidas.forEach(aut -> log.warn(" - ID: {}, Aluno: {}, Ambiente: {}", aut.getId(),
                    aut.getAluno().getNome(), aut.getAmbiente().getNome()));
        }
    }
}
