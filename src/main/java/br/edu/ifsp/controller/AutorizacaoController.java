package br.edu.ifsp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.domain.Autorizacao;
import br.edu.ifsp.dto.AutorizacaoDTO;
import br.edu.ifsp.service.AutorizacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autorizacoes")
@RequiredArgsConstructor
public class AutorizacaoController {

    private final AutorizacaoService service;

    @PostMapping
    public ResponseEntity<Autorizacao> salvar(@RequestBody @Valid AutorizacaoDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Autorizacao>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/aluno/{id}")
    public ResponseEntity<List<Autorizacao>> listarPorAluno(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorAluno(id));
    }

    @GetMapping("/ambiente/{id}")
    public ResponseEntity<List<Autorizacao>> listarPorAmbiente(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorAmbiente(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
