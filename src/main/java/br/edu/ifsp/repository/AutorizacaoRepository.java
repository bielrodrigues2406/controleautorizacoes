package br.edu.ifsp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;

import br.edu.ifsp.domain.Autorizacao;
import br.edu.ifsp.enums.StatusAutorizacao;

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

@Query("""
    SELECT a FROM Autorizacao a
    WHERE (:alunoId IS NULL OR a.aluno.id = :alunoId)
      AND (:ambienteId IS NULL OR a.ambiente.id = :ambienteId)
      AND (:status IS NULL OR a.status = :status)
""")
Page<Autorizacao> filtrarAutorizacoes(
    @Param("alunoId") Long alunoId,
    @Param("ambienteId") Long ambienteId,
    @Param("status") StatusAutorizacao status,
    Pageable pageable
);

}
