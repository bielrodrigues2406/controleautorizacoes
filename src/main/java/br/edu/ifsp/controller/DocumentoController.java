package br.edu.ifsp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifsp.domain.Documento;
import br.edu.ifsp.service.DocumentoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoService service;

    @Operation(summary = "Faz upload de um documento para um aluno")
    @PostMapping(value = "/aluno/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile file) throws IOException {

        Documento doc = service.salvarDocumento(id, file);
        return ResponseEntity.ok("Arquivo enviado: " + doc.getNomeOriginal());
    }

    @Operation(summary = "Lista documentos enviados por um aluno")
    @GetMapping("/aluno/{id}")
    public ResponseEntity<List<String>> listar(@PathVariable Long id) {
        List<Documento> docs = service.listarPorAluno(id);
        List<String> nomes = docs.stream().map(Documento::getNomeOriginal).toList();
        return ResponseEntity.ok(nomes);
    }
}
