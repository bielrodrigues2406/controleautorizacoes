package br.edu.ifsp.controller;

import br.edu.ifsp.dto.DashboardDTO;
import br.edu.ifsp.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/filtro")
    @PreAuthorize("hasAnyRole('SERVIDOR','CAE')")
    @Operation(summary = "Resumo com filtro por período e status")
    public ResponseEntity<DashboardDTO> resumoPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        DashboardDTO resumo = service.gerarResumoFiltrado(inicio, fim, null);
        return ResponseEntity.ok(resumo);
    }
}
