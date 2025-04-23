package br.edu.ifsp.controller;

import br.edu.ifsp.service.RecuperacaoSenhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RecuperacaoSenhaService recuperacaoSenhaService;

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
}
