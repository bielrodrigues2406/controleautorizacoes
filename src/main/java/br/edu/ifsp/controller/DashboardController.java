package br.edu.ifsp.controller;

import br.edu.ifsp.dto.DashboardDTO;
import br.edu.ifsp.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Resumo com contadores de uso do sistema")
    @GetMapping
    public ResponseEntity<DashboardDTO> gerarResumo() {
        return ResponseEntity.ok(dashboardService.gerarResumo());
    }
}
