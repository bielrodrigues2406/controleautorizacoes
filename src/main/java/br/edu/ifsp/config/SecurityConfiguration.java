package br.edu.ifsp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_PUBLICOS = {
        "/auth/login",
        "/auth/recuperar",
        "/auth/redefinir",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    };

    public static final String[] ENDPOINTS_CAE = {
        "/solicitacoes/**",
        "/dashboard/**"
    };

    public static final String[] ENDPOINTS_SERVIDOR = {
        "/autorizacoes/**"
    };

    public static final String[] ENDPOINTS_ALUNO = {
        "/alunos/**",
        "/documentos/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // ✅ Desativa CSRF (necessário para JWT)
            .formLogin(form -> form.disable()) // ✅ Desativa formulário de login padrão
            .httpBasic(basic -> basic.disable()) // ✅ Desativa autenticação Basic
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(ENDPOINTS_PUBLICOS).permitAll()
                .requestMatchers(ENDPOINTS_CAE).hasRole("CAE")
                .requestMatchers(ENDPOINTS_SERVIDOR).hasRole("SERVIDOR")
                .requestMatchers(ENDPOINTS_ALUNO).hasRole("ALUNO")
                .anyRequest().authenticated()
            )
            .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
