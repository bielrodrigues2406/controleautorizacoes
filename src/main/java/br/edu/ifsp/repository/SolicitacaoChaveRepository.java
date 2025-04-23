package br.edu.ifsp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.domain.SolicitacaoChave;
import br.edu.ifsp.enums.StatusChave;

public interface SolicitacaoChaveRepository extends JpaRepository<SolicitacaoChave, Long> {
    List<SolicitacaoChave> findByAlunoId(Long alunoId);

    List<SolicitacaoChave> findByAmbienteId(Long ambienteId);

    List<SolicitacaoChave> findByStatus(StatusChave status);

    long countByStatusAndDataSolicitacaoBetween(StatusChave status, LocalDateTime inicio, LocalDateTime fim);

    long countByStatusAndDataEntregaBetween(StatusChave status, LocalDateTime inicio, LocalDateTime fim);

    long countByStatusAndDataDevolucaoIsNull();

    List<SolicitacaoChave> findByDataSolicitacaoBetween(LocalDateTime inicio, LocalDateTime fim);
    long countByStatus(StatusChave status);


}
