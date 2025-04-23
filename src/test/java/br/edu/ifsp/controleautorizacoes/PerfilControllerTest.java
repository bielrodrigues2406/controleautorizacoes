package br.edu.ifsp.controleautorizacoes;

import java.util.Optional;

import br.edu.ifsp.enums.Role;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.ifsp.controller.PerfilController;
import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.repository.UsuarioRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PerfilController.class)
@AutoConfigureMockMvc(addFilters = false) // desativa segurança para teste
public class PerfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UsuarioRepository usuarioRepository;

    @Test
    public void testGetPerfil() throws Exception {
        Usuario mockUser = new Usuario();
        mockUser.setUsername("aluno@ifsp");
        mockUser.setRole(Role.ALUNO); // ✅ enum importado corretamente

        Aluno aluno = new Aluno();
        aluno.setNome("Gabriel");
        aluno.setCurso("ADS");
        aluno.setEmail("aluno@ifsp");
        aluno.setProntuario("IFSP123");
        aluno.setUsuario(mockUser);
        mockUser.setAluno(aluno);

        Mockito.when(usuarioRepository.findByUsername("aluno@ifsp")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/perfil").principal(() -> "aluno@ifsp"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Gabriel"))
                .andExpect(jsonPath("$.tipo").value("ALUNO"));
    }
}

