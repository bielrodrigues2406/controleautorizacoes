package br.edu.ifsp.service;

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
    private final EmailService emailService;

    /**
     * Executa diariamente às 06:00 - Verifica autorizações vencidas e notifica alunos
     */
    @Scheduled(cron = "0 0 6 * * *")
    public void verificarAutorizacoesVencidas() {
        LocalDate hoje = LocalDate.now();

        List<Autorizacao> vencidas = autorizacaoRepository.findByDataFimBefore(hoje);

        if (vencidas.isEmpty()) {
            log.info("[Agendamento] Nenhuma autorização vencida encontrada para {}", hoje);
            return;
        }

        log.warn("[Agendamento] Autorizações vencidas detectadas em {}: {}", hoje, vencidas.size());

        for (Autorizacao aut : vencidas) {
            String aluno = aut.getAluno().getNome();
            String email = aut.getAluno().getEmail();
            String ambiente = aut.getAmbiente().getNome();
            String data = aut.getDataFim().toString();

            String assunto = "Sua autorização venceu";
            String mensagem = String.format("Olá %s,\nsua autorização para o ambiente \"%s\" venceu em %s.\nProcure a CAE para renovar.", aluno, ambiente, data);

            log.warn("Notificando aluno: {} <{}>", aluno, email);
            emailService.enviar(email, assunto, mensagem);
        }
    }
}
