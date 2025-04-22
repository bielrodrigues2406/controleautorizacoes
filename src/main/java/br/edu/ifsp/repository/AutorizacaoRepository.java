package br.edu.ifsp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.domain.Autorizacao;

public interface AutorizacaoRepository extends JpaRepository<Autorizacao, Long> {
    List<Autorizacao> findByAlunoId(Long alunoId);
    List<Autorizacao> findByAmbienteId(Long ambienteId);
    List<Autorizacao> findByDataFimBefore(LocalDate data);

}
