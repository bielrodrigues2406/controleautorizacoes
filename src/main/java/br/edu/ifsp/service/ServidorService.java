package br.edu.ifsp.service;

import br.edu.ifsp.domain.Servidor;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.dto.ServidorDTO;
import br.edu.ifsp.enums.Role;
import br.edu.ifsp.repository.ServidorRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import br.edu.ifsp.service.shared.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServidorService extends CrudService<Servidor, Long> {

    private final ServidorRepository servidorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected JpaRepository<Servidor, Long> getRepository() {
        return servidorRepository;
    }

    public Servidor salvar(ServidorDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRole(Role.SERVIDOR);

        usuario = usuarioRepository.save(usuario);

        Servidor servidor = new Servidor(null,
                dto.getNome(),
                dto.getProntuario(),
                dto.getEmail(),
                usuario);

        return servidorRepository.save(servidor);
    }

    public Servidor atualizar(Long id, ServidorDTO dto) {
        Servidor servidor = buscarPorId(id); // herdado de CrudService
        servidor.setNome(dto.getNome());
        servidor.setEmail(dto.getEmail());
        return servidorRepository.save(servidor);
    }
}
