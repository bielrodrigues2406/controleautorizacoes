package br.edu.ifsp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.dto.AlunoDTO;
import br.edu.ifsp.repository.AlunoRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public Aluno salvar(AlunoDTO dto) {
        Usuario usuario = usuarioRepository.findByUsername(dto.getUsuarioUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Aluno aluno = new Aluno(null,
                dto.getNome(),
                dto.getProntuario(),
                dto.getCurso(),
                dto.getEmail(),
                usuario);

        return repository.save(aluno);
    }

    public List<Aluno> listar() {
        return repository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public Aluno atualizar(Long id, AlunoDTO dto) {
        Aluno aluno = buscarPorId(id);
        aluno.setNome(dto.getNome());
        aluno.setCurso(dto.getCurso());
        aluno.setEmail(dto.getEmail());
        return repository.save(aluno);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
