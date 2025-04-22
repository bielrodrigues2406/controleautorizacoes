package br.edu.ifsp.controleautorizacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControleautorizacoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleautorizacoesApplication.class, args);
	}

}
