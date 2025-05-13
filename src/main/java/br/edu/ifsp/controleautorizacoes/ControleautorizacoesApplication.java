package br.edu.ifsp.controleautorizacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = "br.edu.ifsp")
@EnableScheduling
@EntityScan(basePackages = "br.edu.ifsp.domain")
@EnableJpaRepositories(basePackages = "br.edu.ifsp.repository")
public class ControleautorizacoesApplication {

	public static void main(String[] args) {
		
		PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("s"));
		//SpringApplication.run(ControleApplication.class, args);
		SpringApplication.run(ControleautorizacoesApplication.class, args);
	}

}