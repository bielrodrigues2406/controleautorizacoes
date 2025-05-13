package br.edu.ifsp.controller;

import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.dto.PerfilDTO;
import br.edu.ifsp.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perfil")
public class PerfilController {

    private final UsuarioRepository usuarioRepository;

    
@Operation(summary = "Retorna os dados do usuário autenticado")
@PreAuthorize("isAuthenticated()")
@ApiResponse(
    responseCode = "200",
    description = "Perfil retornado com sucesso",
    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PerfilDTO.class))
)
    @GetMapping
    public ResponseEntity<PerfilDTO> getPerfil(HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String tipo = usuario.getRole().toString();
        String nome = "Usuário";
        String email = usuario.getUsername();
        String prontuario = null;
        String curso = null;

        if (usuario.getAluno() != null) {
            nome = usuario.getAluno().getNome();
            email = usuario.getAluno().getEmail();
            prontuario = usuario.getAluno().getProntuario();
            curso = usuario.getAluno().getCurso();
        } else if (usuario.getServidor() != null) {
            nome = usuario.getServidor().getNome();
            email = usuario.getServidor().getEmail();
            prontuario = usuario.getServidor().getProntuario();
        }

        PerfilDTO perfil = new PerfilDTO(nome, email, tipo, curso, prontuario);
        return ResponseEntity.ok(perfil);
    }
}

