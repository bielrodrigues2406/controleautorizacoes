package br.edu.ifsp.controleautorizacoes;

import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.repository.TokenRecuperacaoRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import br.edu.ifsp.service.EmailService;
import br.edu.ifsp.service.RecuperacaoSenhaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class RecuperacaoSenhaServiceTest {

    @Mock private TokenRecuperacaoRepository tokenRepo;
    @Mock private UsuarioRepository usuarioRepo;
    @Mock private EmailService emailService;
    @Mock private PasswordEncoder encoder;

    @InjectMocks private RecuperacaoSenhaService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveEnviarEmailComToken() {
        Usuario user = new Usuario();
        user.setUsername("teste@ifsp");

        when(usuarioRepo.findByUsername("teste@ifsp")).thenReturn(Optional.of(user));

        service.gerarToken("teste@ifsp");

        verify(tokenRepo).save(any());
        verify(emailService).enviar(eq("teste@ifsp"), any(), contains("/auth/redefinir?token="));
    }

    @Test
    public void deveFalharSeTokenNaoExistir() {
        when(tokenRepo.findByToken("xyz")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.redefinirSenha("xyz", "123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Token inválido");
    }
}
