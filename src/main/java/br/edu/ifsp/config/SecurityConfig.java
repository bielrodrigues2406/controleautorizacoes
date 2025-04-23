package br.edu.ifsp.config;

import br.edu.ifsp.service.TentativaLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final TentativaLoginService tentativaLoginService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/auth/recuperar", "/auth/redefinir").permitAll()
                        .requestMatchers(HttpMethod.GET, "/alunos/**").hasAnyRole("CAE", "SERVIDOR", "ALUNO")
                        .requestMatchers(HttpMethod.POST, "/autorizacoes/**").hasRole("SERVIDOR")
                        .requestMatchers("/solicitacoes/**").hasAnyRole("CAE", "ALUNO")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .failureHandler(falhaLoginHandler())
                        .successHandler(sucessoLoginHandler()))
                .httpBasic(); // mantém suporte ao basic auth

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public AuthenticationFailureHandler falhaLoginHandler() {
        return (request, response, exception) -> {
            String username = request.getParameter("username");
            if (username != null) {
                tentativaLoginService.registrarFalha(username);
            }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login inválido ou usuário bloqueado.");
        };
    }

    @Bean
    public AuthenticationSuccessHandler sucessoLoginHandler() {
        return (request, response, authentication) -> {
            String username = authentication.getName();
            tentativaLoginService.resetar(username);
            response.setStatus(HttpServletResponse.SC_OK);
        };
    }
}
