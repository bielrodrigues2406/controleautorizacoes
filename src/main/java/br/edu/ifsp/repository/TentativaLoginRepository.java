package br.edu.ifsp.repository;

import br.edu.ifsp.domain.TentativaLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TentativaLoginRepository extends JpaRepository<TentativaLogin, Long> {
    Optional<TentativaLogin> findByUsername(String username);
}
