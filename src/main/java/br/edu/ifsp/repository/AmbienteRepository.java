package br.edu.ifsp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.domain.Ambiente;

public interface AmbienteRepository extends JpaRepository<Ambiente, Long> {
    List<Ambiente> findByDisponivelTrue();
    long countByDisponivelTrue();

}

