package br.edu.ifsp.service;

import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.dto.AlunoDTO;
import br.edu.ifsp.enums.Role;
import br.edu.ifsp.repository.AlunoRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import br.edu.ifsp.service.shared.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService extends CrudService<Aluno, Long> {

    private final AlunoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected JpaRepository<Aluno, Long> getRepository() {
        return repository;
    }

    public Aluno salvar(AlunoDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRole(Role.ALUNO);

        usuario = usuarioRepository.save(usuario);

        Aluno aluno = new Aluno(null,
                dto.getNome(),
                dto.getProntuario(),
                dto.getCurso(),
                dto.getEmail(),
                usuario);

        return repository.save(aluno);
    }

    public Aluno atualizar(Long id, AlunoDTO dto) {
        Aluno aluno = buscarPorId(id); 
        aluno.setNome(dto.getNome());
        aluno.setCurso(dto.getCurso());
        aluno.setEmail(dto.getEmail());
        return repository.save(aluno);
    }
}
