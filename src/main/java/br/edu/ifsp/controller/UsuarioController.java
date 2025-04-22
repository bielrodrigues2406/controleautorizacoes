package br.edu.ifsp.controller;

import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.enums.Role;
import br.edu.ifsp.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Cria um novo usuário")
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario salvo = usuarioRepository.save(usuario);
        URI uri = URI.create("/usuarios/" + salvo.getId());
        return ResponseEntity.created(uri).body(salvo);
    }

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @Operation(summary = "Busca um usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualiza um usuário existente")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioRepository.findById(id).map(u -> {
            u.setUsername(usuario.getUsername());
            if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
                u.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            u.setRole(usuario.getRole());
            return ResponseEntity.ok(usuarioRepository.save(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Remove um usuário por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
