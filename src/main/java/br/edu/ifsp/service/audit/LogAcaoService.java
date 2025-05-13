package br.edu.ifsp.service.audit;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.edu.ifsp.domain.LogAcao;
import br.edu.ifsp.repository.LogAcaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogAcaoService {

    private final LogAcaoRepository repository;

    public void registrar(String usuario, String acao, String entidade, Long refId) {
        LogAcao log = new LogAcao();
        log.setUsuario(usuario);
        log.setAcao(acao);
        log.setEntidade(entidade);
        log.setReferenciaId(refId);
        log.setDataHora(LocalDateTime.now());
        repository.save(log);
    }
}
