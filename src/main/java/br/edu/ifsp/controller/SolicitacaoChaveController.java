package br.edu.ifsp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.domain.SolicitacaoChave;
import br.edu.ifsp.dto.SolicitacaoChaveDTO;
import br.edu.ifsp.service.SolicitacaoChaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoChaveController {

    private final SolicitacaoChaveService service;

    @PostMapping
    public ResponseEntity<SolicitacaoChave> solicitar(@RequestBody @Valid SolicitacaoChaveDTO dto) {
        return ResponseEntity.ok(service.solicitar(dto));
    }

    @PutMapping("/{id}/entregar")
    public ResponseEntity<SolicitacaoChave> entregar(@PathVariable Long id) {
        return ResponseEntity.ok(service.entregar(id));
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<SolicitacaoChave> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(service.devolver(id));
    }

    @GetMapping
    public ResponseEntity<List<SolicitacaoChave>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}

