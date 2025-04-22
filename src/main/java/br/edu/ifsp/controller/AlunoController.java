package br.edu.ifsp.controller;

import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.dto.AlunoDTO;
import br.edu.ifsp.mapper.AlunoMapper;
import br.edu.ifsp.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;
    private final AlunoMapper mapper;

    @Operation(summary = "Lista todos os alunos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listar() {
        List<Aluno> lista = service.listar();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(mapper.alunoListToAlunoDTOList(lista));
    }

    @Operation(summary = "Busca um aluno por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Aluno encontrado",
                content = @Content(schema = @Schema(implementation = AlunoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscar(@PathVariable Long id) {
        Aluno aluno = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.alunoToAlunoDTO(aluno));
    }

    @Operation(summary = "Cadastra um novo aluno")
    @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso")
    @PostMapping
    public ResponseEntity<AlunoDTO> salvar(@RequestBody @Valid AlunoDTO dto, UriComponentsBuilder uriBuilder) {
        Aluno aluno = service.salvar(dto);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.alunoToAlunoDTO(aluno));
    }

    @Operation(summary = "Atualiza os dados de um aluno existente")
    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AlunoDTO dto) {
        Aluno atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(mapper.alunoToAlunoDTO(atualizado));
    }

    @Operation(summary = "Remove um aluno pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
