package br.edu.ifsp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.domain.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByAlunoId(Long alunoId);
}
