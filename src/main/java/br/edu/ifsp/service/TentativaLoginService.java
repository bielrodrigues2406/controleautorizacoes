package br.edu.ifsp.service;

import br.edu.ifsp.domain.TentativaLogin;
import br.edu.ifsp.repository.TentativaLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TentativaLoginService {

    private final TentativaLoginRepository repository;
    private static final int MAX_TENTATIVAS = 3;
    private static final int BLOQUEIO_MINUTOS = 15;

    public void registrarFalha(String username) {
        TentativaLogin tentativa = repository.findByUsername(username)
                .orElse(new TentativaLogin(null, username, 0, null, false, null));

        tentativa.setTentativas(tentativa.getTentativas() + 1);
        tentativa.setUltimoErro(LocalDateTime.now());

        if (tentativa.getTentativas() >= MAX_TENTATIVAS) {
            tentativa.setBloqueado(true);
            tentativa.setDesbloqueioApos(LocalDateTime.now().plusMinutes(BLOQUEIO_MINUTOS));
        }

        repository.save(tentativa);
    }

    public void resetar(String username) {
        repository.findByUsername(username).ifPresent(t -> {
            t.setTentativas(0);
            t.setBloqueado(false);
            t.setDesbloqueioApos(null);
            repository.save(t);
        });
    }

    public boolean estaBloqueado(String username) {
        return repository.findByUsername(username)
                .map(t -> t.isBloqueado() &&
                          (t.getDesbloqueioApos() == null || t.getDesbloqueioApos().isAfter(LocalDateTime.now())))
                .orElse(false);
    }
}
