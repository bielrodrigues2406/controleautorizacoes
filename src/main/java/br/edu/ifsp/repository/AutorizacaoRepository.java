package br.edu.ifsp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.ifsp.domain.Autorizacao;

public interface AutorizacaoRepository extends JpaRepository<Autorizacao, Long> {
    List<Autorizacao> findByAlunoId(Long alunoId);
    List<Autorizacao> findByAmbienteId(Long ambienteId);
    List<Autorizacao> findByDataFimBefore(LocalDate data);
@Query("""
    SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
    FROM Autorizacao a
    WHERE a.aluno.id = :alunoId
      AND a.ambiente.id = :ambienteId
      AND CURRENT_DATE BETWEEN a.dataInicio AND a.dataFim
""")
boolean existeAutorizacaoAtiva(Long alunoId, Long ambienteId);
}
