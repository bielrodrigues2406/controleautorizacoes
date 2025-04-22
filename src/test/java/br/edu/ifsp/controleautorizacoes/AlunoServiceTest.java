package br.edu.ifsp.controleautorizacoes;
import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.dto.AlunoDTO;
import br.edu.ifsp.enums.Role;
import br.edu.ifsp.repository.AlunoRepository;
import br.edu.ifsp.repository.UsuarioRepository;
import br.edu.ifsp.service.AlunoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveSalvarAlunoComUsuario() {
        // Arrange
        AlunoDTO dto = new AlunoDTO();
        dto.setNome("Gabriel");
        dto.setEmail("gabriel@ifsp");
        dto.setProntuario("IFSP001");
        dto.setCurso("ADS");
        dto.setUsername("gabriel@ifsp");
        dto.setPassword("123456");

        when(passwordEncoder.encode("123456")).thenReturn("encoded123");

        Usuario usuarioSalvo = new Usuario(1L, "gabriel@ifsp", "encoded123", Role.ALUNO, null, null);
        when(usuarioRepository.save(any())).thenReturn(usuarioSalvo);

        Aluno alunoSalvo = new Aluno(1L, dto.getNome(), dto.getProntuario(), dto.getCurso(), dto.getEmail(), usuarioSalvo);
        when(alunoRepository.save(any())).thenReturn(alunoSalvo);

        // Act
        Aluno resultado = alunoService.salvar(dto);

        // Assert
        assertThat(resultado.getUsuario().getUsername()).isEqualTo("gabriel@ifsp");
        assertThat(resultado.getUsuario().getPassword()).isEqualTo("encoded123");

        verify(usuarioRepository).save(any());
        verify(alunoRepository).save(any());
    }
}
