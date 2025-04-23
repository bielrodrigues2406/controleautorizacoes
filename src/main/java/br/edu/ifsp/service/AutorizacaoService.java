package br.edu.ifsp.service;

import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.domain.Ambiente;
import br.edu.ifsp.domain.Autorizacao;
import br.edu.ifsp.domain.Servidor;
import br.edu.ifsp.dto.AutorizacaoDTO;
import br.edu.ifsp.enums.StatusAutorizacao;
import br.edu.ifsp.repository.*;
import br.edu.ifsp.service.shared.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorizacaoService extends CrudService<Autorizacao, Long> {

    private final AutorizacaoRepository repository;
    private final AlunoRepository alunoRepository;
    private final ServidorRepository servidorRepository;
    private final AmbienteRepository ambienteRepository;

    @Override
    protected JpaRepository<Autorizacao, Long> getRepository() {
        return repository;
    }

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

    public List<Autorizacao> listarPorAluno(Long alunoId) {
        return repository.findByAlunoId(alunoId);
    }

    public List<Autorizacao> listarPorAmbiente(Long ambienteId) {
        return repository.findByAmbienteId(ambienteId);
    }

    public boolean alunoAutorizado(Long alunoId, Long ambienteId) {
        return repository.existeAutorizacaoAtiva(alunoId, ambienteId);
    }

    public org.springframework.data.domain.Page<Autorizacao> filtrar(
            Long alunoId, Long ambienteId, StatusAutorizacao status, Pageable pageable) {
        return repository.filtrarAutorizacoes(alunoId, ambienteId, status, pageable);
    }
}
