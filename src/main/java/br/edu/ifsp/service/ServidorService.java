package br.edu.ifsp.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifsp.domain.Servidor;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.dto.ServidorDTO;
import br.edu.ifsp.enums.Role;
import br.edu.ifsp.repository.ServidorRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServidorService {

    private final ServidorRepository servidorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

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

    public List<Servidor> listar() {
        return servidorRepository.findAll();
    }

    public Servidor buscarPorId(Long id) {
        return servidorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servidor não encontrado"));
    }

    public Servidor atualizar(Long id, ServidorDTO dto) {
        Servidor servidor = buscarPorId(id);
        servidor.setNome(dto.getNome());
        servidor.setEmail(dto.getEmail());
        return servidorRepository.save(servidor);
    }

    public void deletar(Long id) {
        servidorRepository.deleteById(id);
    }
}

