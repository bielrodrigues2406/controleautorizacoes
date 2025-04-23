package br.edu.ifsp.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Service;

import br.edu.ifsp.repository.UsuarioRepository;
import br.edu.ifsp.service.TentativaLoginService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final TentativaLoginService tentativaLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 🔒 Verifica se está bloqueado
        if (tentativaLoginService.estaBloqueado(username)) {
            throw new LockedException("Usuário bloqueado por tentativas inválidas. Tente novamente mais tarde.");
        }

        return usuarioRepository.findByUsername(username)
                .map(usuario -> User.builder()
                        .username(usuario.getUsername())
                        .password(usuario.getPassword())
                        .roles(usuario.getRole().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
