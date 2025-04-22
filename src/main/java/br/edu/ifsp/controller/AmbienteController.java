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

import br.edu.ifsp.domain.Ambiente;
import br.edu.ifsp.dto.AmbienteDTO;
import br.edu.ifsp.mapper.AmbienteMapper;
import br.edu.ifsp.service.AmbienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ambientes")
public class AmbienteController {

    private final AmbienteService service;
    private final AmbienteMapper mapper;

    @Operation(summary = "Lista todos os ambientes cadastrados")
    @GetMapping
    public ResponseEntity<List<AmbienteDTO>> listar() {
        List<Ambiente> lista = service.listar();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(mapper.ambienteListToAmbienteDTOList(lista));
    }

    @Operation(summary = "Lista os ambientes disponíveis")
    @GetMapping("/disponiveis")
    public ResponseEntity<List<AmbienteDTO>> listarDisponiveis() {
        List<Ambiente> lista = service.listarDisponiveis();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(mapper.ambienteListToAmbienteDTOList(lista));
    }

   @Operation(summary = "Busca um ambiente por ID")
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Ambiente encontrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = AmbienteDTO.class)
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Ambiente não encontrado",
        content = @Content
    )
})
@GetMapping("/{id}")
public ResponseEntity<AmbienteDTO> buscar(@PathVariable Long id) {
    Ambiente ambiente = service.buscarPorId(id);
    return ResponseEntity.ok(mapper.ambienteToAmbienteDTO(ambiente));
}

    @Operation(summary = "Cadastra um novo ambiente")
    @PostMapping
    public ResponseEntity<AmbienteDTO> salvar(@RequestBody @Valid AmbienteDTO dto, UriComponentsBuilder uriBuilder) {
        Ambiente ambiente = service.salvar(dto);
        URI uri = uriBuilder.path("/ambientes/{id}").buildAndExpand(ambiente.getId()).toUri();
        return ResponseEntity.created(uri).body(mapper.ambienteToAmbienteDTO(ambiente));
    }

    @Operation(summary = "Atualiza um ambiente existente")
    @PutMapping("/{id}")
    public ResponseEntity<AmbienteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AmbienteDTO dto) {
        Ambiente atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(mapper.ambienteToAmbienteDTO(atualizado));
    }

    @Operation(summary = "Remove um ambiente pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
