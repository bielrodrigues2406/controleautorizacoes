package br.edu.ifsp.service;

import br.edu.ifsp.domain.*;
import br.edu.ifsp.dto.SolicitacaoChaveDTO;
import br.edu.ifsp.enums.StatusChave;
import br.edu.ifsp.repository.AlunoRepository;
import br.edu.ifsp.repository.AmbienteRepository;
import br.edu.ifsp.repository.SolicitacaoChaveRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorizacaoChaveService {

    private final SolicitacaoChaveRepository repository;
    private final AlunoRepository alunoRepository;
    private final AmbienteRepository ambienteRepository;

    @Transactional
    public SolicitacaoChave solicitar(SolicitacaoChaveDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Ambiente ambiente = ambienteRepository.findById(dto.getAmbienteId())
                .orElseThrow(() -> new RuntimeException("Ambiente não encontrado"));

        SolicitacaoChave solicitacao = new SolicitacaoChave();
        solicitacao.setAluno(aluno);
        solicitacao.setAmbiente(ambiente);
        solicitacao.setStatus(StatusChave.SOLICITADA);
        solicitacao.setDataSolicitacao(LocalDateTime.now());

        return repository.save(solicitacao);
    }

    @Transactional
    public SolicitacaoChave entregar(Long id) {
        SolicitacaoChave solicitacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        solicitacao.setStatus(StatusChave.ENTREGUE);
        solicitacao.setDataEntrega(LocalDateTime.now());

        return repository.save(solicitacao);
    }

    @Transactional
    public SolicitacaoChave devolver(Long id) {
        SolicitacaoChave solicitacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));

        solicitacao.setStatus(StatusChave.DEVOLVIDA);
        solicitacao.setDataDevolucao(LocalDateTime.now());

        return repository.save(solicitacao);
    }

    public List<SolicitacaoChave> listar() {
        return repository.findAll();
    }

    public List<SolicitacaoChave> listarPorAluno(Long alunoId) {
        return repository.findByAlunoId(alunoId);
    }

    public List<SolicitacaoChave> listarPorAmbiente(Long ambienteId) {
        return repository.findByAmbienteId(ambienteId);
    }

    public List<SolicitacaoChave> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDataSolicitacaoBetween(inicio, fim);
    }
}
