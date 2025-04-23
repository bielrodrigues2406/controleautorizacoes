package br.edu.ifsp.controller;

import br.edu.ifsp.dto.DashboardDTO;
import br.edu.ifsp.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Retorna o resumo geral do sistema")
    @ApiResponse(responseCode = "200", description = "Resumo retornado com sucesso")
    @GetMapping
    public ResponseEntity<DashboardDTO> gerarResumo() {
        return ResponseEntity.ok(dashboardService.gerarResumo());
    }
}
