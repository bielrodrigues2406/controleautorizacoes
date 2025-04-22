package br.edu.ifsp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.domain.Servidor;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {
    Optional<Servidor> findByProntuario(String prontuario);
}
