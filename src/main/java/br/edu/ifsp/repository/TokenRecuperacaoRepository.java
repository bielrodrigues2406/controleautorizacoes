package br.edu.ifsp.repository;

import br.edu.ifsp.domain.TokenRecuperacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRecuperacaoRepository extends JpaRepository<TokenRecuperacao, Long> {
    Optional<TokenRecuperacao> findByToken(String token);
}
