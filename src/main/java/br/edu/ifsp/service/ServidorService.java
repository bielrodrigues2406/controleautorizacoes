package br.edu.ifsp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifsp.domain.Servidor;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.dto.ServidorDTO;
import br.edu.ifsp.repository.ServidorRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServidorService {

    private final ServidorRepository repository;
    private final UsuarioRepository usuarioRepository; // ✅ instância correta injetada

    public Servidor salvar(ServidorDTO dto) {
        Usuario usuario = usuarioRepository.findByUsername(dto.getUsuarioUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Servidor servidor = new Servidor(null,
                dto.getNome(),
                dto.getProntuario(),
                dto.getEmail(),
                usuario);

        return repository.save(servidor);
    }

    public List<Servidor> listar() {
        return repository.findAll();
    }

    public Servidor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servidor não encontrado"));
    }

    public Servidor atualizar(Long id, ServidorDTO dto) {
        Servidor servidor = buscarPorId(id);
        servidor.setNome(dto.getNome());
        servidor.setEmail(dto.getEmail());
        return repository.save(servidor);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
