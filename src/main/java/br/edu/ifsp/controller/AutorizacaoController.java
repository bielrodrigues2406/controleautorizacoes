package br.edu.ifsp.controller;

import br.edu.ifsp.domain.Autorizacao;
import br.edu.ifsp.dto.AutorizacaoDTO;
import br.edu.ifsp.enums.StatusAutorizacao;
import br.edu.ifsp.mapper.AutorizacaoMapper;
import br.edu.ifsp.service.AutorizacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autorizacoes")
public class AutorizacaoController {

    private final AutorizacaoService service;
    private final AutorizacaoMapper mapper;

    @Operation(summary = "Lista todas as autorizações cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/todas")
    public ResponseEntity<List<AutorizacaoDTO>> listar() {
        List<Autorizacao> lista = service.listar();
        return ResponseEntity.ok(mapper.autorizacaoListToAutorizacaoDTOList(lista));
    }

    @Operation(summary = "Cadastra uma nova autorização de uso")
    @ApiResponse(responseCode = "201", description = "Autorização criada com sucesso")
    @PostMapping
    public ResponseEntity<AutorizacaoDTO> salvar(@RequestBody @Valid AutorizacaoDTO dto,
                                                 UriComponentsBuilder uriBuilder) {
        Autorizacao autorizacao = service.salvar(dto);
        URI uri = uriBuilder.path("/autorizacoes/{id}").buildAndExpand(autorizacao.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.autorizacaoToAutorizacaoDTO(autorizacao));
    }

    @Operation(summary = "Lista autorizações de um aluno")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autorizações do aluno encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorizacaoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não possui autorizações", content = @Content)
    })
    @GetMapping("/aluno/{id}")
    public ResponseEntity<List<AutorizacaoDTO>> listarPorAluno(@PathVariable Long id) {
        List<Autorizacao> lista = service.listarPorAluno(id);
        return ResponseEntity.ok(mapper.autorizacaoListToAutorizacaoDTOList(lista));
    }

    @Operation(summary = "Lista autorizações por ambiente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autorizações do ambiente encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutorizacaoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ambiente não possui autorizações", content = @Content)
    })
    @GetMapping("/ambiente/{id}")
    public ResponseEntity<List<AutorizacaoDTO>> listarPorAmbiente(@PathVariable Long id) {
        List<Autorizacao> lista = service.listarPorAmbiente(id);
        return ResponseEntity.ok(mapper.autorizacaoListToAutorizacaoDTOList(lista));
    }

    @Operation(summary = "Verifica se um aluno está autorizado para um ambiente no momento atual")
    @GetMapping("/aluno/{alunoId}/autorizado")
    @PreAuthorize("hasAnyRole('CAE', 'SERVIDOR', 'ALUNO')")
    public ResponseEntity<Boolean> verificarAutorizacaoAtiva(
            @PathVariable Long alunoId,
            @RequestParam Long ambienteId) {
        boolean autorizado = service.alunoAutorizado(alunoId, ambienteId);
        return ResponseEntity.ok(autorizado);
    }

    @Operation(summary = "Remove uma autorização por ID")
    @ApiResponse(responseCode = "204", description = "Autorização removida com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Filtra autorizações com suporte a paginação")
    @GetMapping
    @PreAuthorize("hasAnyRole('SERVIDOR', 'CAE')")
    public ResponseEntity<Page<AutorizacaoDTO>> filtrar(
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) Long ambienteId,
            @RequestParam(required = false) StatusAutorizacao status,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {

        Page<Autorizacao> page = service.filtrar(alunoId, ambienteId, status, pageable);
        return ResponseEntity.ok(page.map(mapper::autorizacaoToAutorizacaoDTO));
    }
}
