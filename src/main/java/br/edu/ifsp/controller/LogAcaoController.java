package br.edu.ifsp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.domain.LogAcao;
import br.edu.ifsp.repository.LogAcaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class LogAcaoController {

    private final LogAcaoRepository repository;

    @GetMapping
    public ResponseEntity<List<LogAcao>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @Operation(summary = "Lista os logs de ações do sistema")
    @GetMapping("/usuario/{username}")
    public ResponseEntity<List<LogAcao>> listarPorUsuario(@PathVariable String username) {
        return ResponseEntity.ok(repository.findByUsuario(username));
    }
}
