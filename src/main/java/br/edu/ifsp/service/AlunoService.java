package br.edu.ifsp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.dto.AlunoDTO;
import br.edu.ifsp.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;

    public Aluno salvar(AlunoDTO dto) {
        Aluno aluno = new Aluno(null, dto.getNome(), dto.getProntuario(), dto.getCurso());
        return repository.save(aluno);
    }

    public List<Aluno> listar() {
        return repository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public Aluno atualizar(Long id, AlunoDTO dto) {
        Aluno aluno = buscarPorId(id);
        aluno.setNome(dto.getNome());
        aluno.setCurso(dto.getCurso());
        return repository.save(aluno);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
