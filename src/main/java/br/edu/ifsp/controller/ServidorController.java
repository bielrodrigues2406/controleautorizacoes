package br.edu.ifsp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.edu.ifsp.domain.Servidor;
import br.edu.ifsp.dto.ServidorDTO;
import br.edu.ifsp.mapper.ServidorMapper;
import br.edu.ifsp.service.ServidorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servidores")
public class ServidorController {

    private final ServidorService service;
    private final ServidorMapper mapper;

    @Operation(summary = "Lista todos os servidores cadastrados")
    @GetMapping
    public ResponseEntity<List<ServidorDTO>> listar() {
        List<Servidor> lista = service.listar();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(mapper.servidorListToServidorDTOList(lista));
    }

    @Operation(summary = "Busca um servidor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServidorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Servidor não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServidorDTO> buscar(@PathVariable Long id) {
        Servidor servidor = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.servidorToServidorDTO(servidor));
    }

    @Operation(summary = "Cadastra um novo servidor")
    @ApiResponse(responseCode = "201", description = "Servidor criado com sucesso")
    @PostMapping
    public ResponseEntity<ServidorDTO> salvar(@RequestBody @Valid ServidorDTO dto, UriComponentsBuilder uriBuilder) {
        Servidor servidor = service.salvar(dto);
        URI uri = uriBuilder.path("/servidores/{id}").buildAndExpand(servidor.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.servidorToServidorDTO(servidor));
    }

    @Operation(summary = "Atualiza um servidor existente")
    @PutMapping("/{id}")
    public ResponseEntity<ServidorDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ServidorDTO dto) {
        Servidor atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(mapper.servidorToServidorDTO(atualizado));
    }

    @Operation(summary = "Remove um servidor por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
