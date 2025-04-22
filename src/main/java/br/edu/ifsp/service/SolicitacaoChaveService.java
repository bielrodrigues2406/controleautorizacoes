package br.edu.ifsp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.domain.Ambiente;
import br.edu.ifsp.domain.SolicitacaoChave;
import br.edu.ifsp.dto.SolicitacaoChaveDTO;
import br.edu.ifsp.enums.StatusChave;
import br.edu.ifsp.repository.AlunoRepository;
import br.edu.ifsp.repository.AmbienteRepository;
import br.edu.ifsp.repository.SolicitacaoChaveRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitacaoChaveService {

    private final SolicitacaoChaveRepository repository;
    private final AlunoRepository alunoRepository;
    private final AmbienteRepository ambienteRepository;

    public SolicitacaoChave solicitar(SolicitacaoChaveDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Ambiente ambiente = ambienteRepository.findById(dto.getAmbienteId())
            .orElseThrow(() -> new RuntimeException("Ambiente não encontrado"));

        SolicitacaoChave sc = new SolicitacaoChave();
        sc.setAluno(aluno);
        sc.setAmbiente(ambiente);
        sc.setStatus(StatusChave.SOLICITADA);
        sc.setDataSolicitacao(LocalDateTime.now());

        return repository.save(sc);
    }

    public SolicitacaoChave entregar(Long id) {
        SolicitacaoChave sc = repository.findById(id).orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        sc.setStatus(StatusChave.ENTREGUE);
        sc.setDataEntrega(LocalDateTime.now());
        return repository.save(sc);
    }

    public SolicitacaoChave devolver(Long id) {
        SolicitacaoChave sc = repository.findById(id).orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        sc.setStatus(StatusChave.DEVOLVIDA);
        sc.setDataDevolucao(LocalDateTime.now());
        return repository.save(sc);
    }

    public List<SolicitacaoChave> listar() {
        return repository.findAll();
    }
}
