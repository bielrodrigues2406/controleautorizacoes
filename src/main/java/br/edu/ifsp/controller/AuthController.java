package br.edu.ifsp.controller;

import br.edu.ifsp.config.JwtTokenService;
import br.edu.ifsp.dto.LoginDTO;
import br.edu.ifsp.enums.Role;
import br.edu.ifsp.repository.UsuarioRepository;
import br.edu.ifsp.service.RecuperacaoSenhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RecuperacaoSenhaService recuperacaoSenhaService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Operation(summary = "Solicita link de recuperação de senha por e-mail")
    @ApiResponse(responseCode = "200", description = "E-mail de recuperação enviado")
    @PostMapping("/recuperar")
    public ResponseEntity<String> solicitarRecuperacao(@RequestParam String email) {
        recuperacaoSenhaService.gerarToken(email);
        return ResponseEntity.ok("E-mail de recuperação enviado.");
    }

    @Operation(summary = "Redefine senha com token de recuperação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Token inválido ou expirado")
    })
    @PostMapping("/redefinir")
    public ResponseEntity<String> redefinirSenha(
            @RequestParam String token,
            @RequestParam String novaSenha) {

        recuperacaoSenhaService.redefinirSenha(token, novaSenha);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
    return usuarioRepository.findByUsername(loginDTO.getUsername())
            .map(usuario -> {
                if (passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
                    UserDetails userDetails = org.springframework.security.core.userdetails.User
                            .withUsername(usuario.getUsername())
                            .password(usuario.getPassword()) // já criptografada
                            .roles(usuario.getRole().name())
                            .build();

                    String token = jwtTokenService.generateToken(userDetails);

                    return ResponseEntity.ok(Map.of(
                            "status", "ok",
                            "tipo", usuario.getRole().name(),
                            "token", token
                    ));
                } else {
                    return ResponseEntity.status(401).body(Map.of(
                            "status", "erro",
                            "message", "Senha inválida"
                    ));
                }
            })
            .orElse(ResponseEntity.status(404).body(Map.of(
                    "status", "erro",
                    "message", "Usuário não encontrado"
            )));
}

}
