package br.edu.ifsp.service;

import java.util.List;

import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.domain.Ambiente;
import br.edu.ifsp.domain.Autorizacao;
import br.edu.ifsp.domain.Servidor;
import br.edu.ifsp.dto.AutorizacaoDTO;
import br.edu.ifsp.enums.StatusAutorizacao;
import br.edu.ifsp.repository.AlunoRepository;
import br.edu.ifsp.repository.AmbienteRepository;
import br.edu.ifsp.repository.AutorizacaoRepository;
import br.edu.ifsp.repository.ServidorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorizacaoService {

    private final AutorizacaoRepository repository;
    private final AlunoRepository alunoRepository;
    private final ServidorRepository servidorRepository;
    private final AmbienteRepository ambienteRepository;

    public Autorizacao salvar(AutorizacaoDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        Servidor servidor = servidorRepository.findById(dto.getServidorId())
            .orElseThrow(() -> new RuntimeException("Servidor não encontrado"));
        Ambiente ambiente = ambienteRepository.findById(dto.getAmbienteId())
            .orElseThrow(() -> new RuntimeException("Ambiente não encontrado"));

          Autorizacao autorizacao = new Autorizacao(null,
        servidor,
        aluno,
        ambiente,
        dto.getAtividade(),
        dto.getDiaSemana(),
        dto.getHoraInicio(),
        dto.getHoraFim(),
        dto.getDataInicio(),
        dto.getDataFim(),
        StatusAutorizacao.ATIVA
);

        return repository.save(autorizacao);
    }

    public List<Autorizacao> listar() {
        return repository.findAll();
    }

    public List<Autorizacao> listarPorAluno(Long alunoId) {
        return repository.findByAlunoId(alunoId);
    }

    public List<Autorizacao> listarPorAmbiente(Long ambienteId) {
        return repository.findByAmbienteId(ambienteId);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public boolean alunoAutorizado(Long alunoId, Long ambienteId) {
        return repository.existeAutorizacaoAtiva(alunoId, ambienteId);
    }

     public Page<Autorizacao> filtrar(Long alunoId, Long ambienteId, StatusAutorizacao status, Pageable pageable) {
        return repository.filtrarAutorizacoes(alunoId, ambienteId, status, pageable);
    }
}

