package br.edu.ifsp.controller;

import br.edu.ifsp.service.RecuperacaoSenhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RecuperacaoSenhaService recuperacaoSenhaService;

    @PostMapping("/recuperar")
    public ResponseEntity<String> solicitarRecuperacao(@RequestParam String email) {
        recuperacaoSenhaService.gerarToken(email);
        return ResponseEntity.ok("E-mail de recuperação enviado.");
    }

    @PostMapping("/redefinir")
    public ResponseEntity<String> redefinirSenha(
            @RequestParam String token,
            @RequestParam String novaSenha) {

        recuperacaoSenhaService.redefinirSenha(token, novaSenha);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}
