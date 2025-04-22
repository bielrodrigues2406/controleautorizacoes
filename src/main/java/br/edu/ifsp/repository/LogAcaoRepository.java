package br.edu.ifsp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.domain.LogAcao;

public interface LogAcaoRepository extends JpaRepository<LogAcao, Long> {
    List<LogAcao> findByUsuario(String usuario);
}
