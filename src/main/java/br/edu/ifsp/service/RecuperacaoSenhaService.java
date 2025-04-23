package br.edu.ifsp.service;

import br.edu.ifsp.domain.TokenRecuperacao;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.repository.TokenRecuperacaoRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecuperacaoSenhaService {

    private final TokenRecuperacaoRepository tokenRepo;
    private final UsuarioRepository usuarioRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void gerarToken(String email) {
        Usuario usuario = usuarioRepo.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = UUID.randomUUID().toString();

        TokenRecuperacao rec = new TokenRecuperacao();
        rec.setToken(token);
        rec.setEmail(email);
        rec.setExpiracao(LocalDateTime.now().plusMinutes(30));
        rec.setUsado(false);

        tokenRepo.save(rec);

        String link = "http://localhost:8080/auth/redefinir?token=" + token;

        emailService.enviar(email,
                "Redefinição de Senha",
                "Clique no link para redefinir sua senha: " + link);
    }

    public void redefinirSenha(String token, String novaSenha) {
        TokenRecuperacao rec = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (rec.isUsado() || rec.getExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado ou já utilizado");
        }

        Usuario usuario = usuarioRepo.findByUsername(rec.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setPassword(passwordEncoder.encode(novaSenha));
        usuarioRepo.save(usuario);

        rec.setUsado(true);
        tokenRepo.save(rec);
    }
}
