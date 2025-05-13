package br.edu.ifsp.controleautorizacoes;

import br.edu.ifsp.domain.TokenRecuperacao;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.enums.Role;
import br.edu.ifsp.repository.TokenRecuperacaoRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private TokenRecuperacaoRepository tokenRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    private final String email = "teste@ifsp.br";
    private final String senhaOriginal = "senhaAntiga";

    @BeforeEach
    void setup() {
        tokenRepo.deleteAll();
        usuarioRepo.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setUsername(email);
        usuario.setPassword(passwordEncoder.encode(senhaOriginal));
        usuario.setRole(Role.ALUNO);
        usuarioRepo.save(usuario);
    }

    @Test
    public void fluxoCompletoRecuperacaoESucesso() throws Exception {
        // 1. Solicita token
        mockMvc.perform(post("/auth/recuperar")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("E-mail de recuperação enviado."));

        // 2. Captura token real do banco
        TokenRecuperacao token = tokenRepo.findByToken(tokenRepo.findAll().get(0).getToken())
                .orElseThrow();

        // 3. Redefine com token válido
        String novaSenha = "novaSenhaSegura456";

        mockMvc.perform(post("/auth/redefinir")
                .param("token", token.getToken())
                .param("novaSenha", novaSenha))
                .andExpect(status().isOk())
                .andExpect(content().string("Senha redefinida com sucesso."));

        // 4. Verifica se a senha foi realmente trocada
        Usuario atualizado = usuarioRepo.findByUsername(email).orElseThrow();
        assertThat(passwordEncoder.matches(novaSenha, atualizado.getPassword())).isTrue();
    }

    @Test
    public void falhaComTokenInvalido() throws Exception {
        mockMvc.perform(post("/auth/redefinir")
                .param("token", UUID.randomUUID().toString())
                .param("novaSenha", "qualquer123"))
                .andExpect(status().isInternalServerError());
    }

    @Test
public void loginBemSucedido() throws Exception {
    String body = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", email, senhaOriginal);

    mockMvc.perform(post("/auth/login")
            .contentType("application/json")
            .content(body))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("ok"))
            .andExpect(jsonPath("$.tipo").value("ALUNO"));
}

@Test
public void loginComSenhaIncorreta() throws Exception {
    String body = String.format("{\"username\": \"%s\", \"password\": \"errada123\"}", email);

    mockMvc.perform(post("/auth/login")
            .contentType("application/json")
            .content(body))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value("erro"))
            .andExpect(jsonPath("$.message").value("Senha inválida"));
}

@Test
public void loginUsuarioInexistente() throws Exception {
    String body = "{\"username\": \"inexistente@ifsp\", \"password\": \"123\"}";

    mockMvc.perform(post("/auth/login")
            .contentType("application/json")
            .content(body))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value("erro"))
            .andExpect(jsonPath("$.message").value("Usuário não encontrado"));
}

}
