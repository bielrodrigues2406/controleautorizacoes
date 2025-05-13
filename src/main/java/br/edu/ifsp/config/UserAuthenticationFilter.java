package br.edu.ifsp.config;

import br.edu.ifsp.domain.Usuario;
import br.edu.ifsp.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        System.out.println("➡️ Requisição recebida: " + request.getMethod() + " " + request.getRequestURI());

        String token = recuperarToken(request);
        System.out.println("🔎 Token encontrado no header: " + token);

        if (token != null) {
            try {
                String username = jwtTokenService.getSubjectFromToken(token);
                System.out.println("✅ Username extraído do token: " + username);

                Usuario usuario = usuarioRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                System.out.println("👤 Usuário encontrado no banco: " + usuario.getUsername() + " | Perfil: " + usuario.getRole());

                DetalhesUsuario userDetails = new DetalhesUsuario(usuario);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("🔐 Autenticação inserida no contexto");

            } catch (Exception e) {
                System.out.println("❌ Erro na autenticação via token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado: " + e.getMessage());
                return;
            }
        } else if (endpointExigeAutenticacao(request)) {
            System.out.println("🚫 Token ausente em endpoint protegido: " + request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token ausente");
            return;
        } else {
            System.out.println("🟢 Endpoint público: " + request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }

    private boolean endpointExigeAutenticacao(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !Arrays.asList(SecurityConfiguration.ENDPOINTS_PUBLICOS).contains(uri);
    }
}