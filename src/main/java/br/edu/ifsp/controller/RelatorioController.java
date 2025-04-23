package br.edu.ifsp.controller;

import br.edu.ifsp.enums.StatusAutorizacao;
import br.edu.ifsp.service.RelatorioService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/exportar")
    @PreAuthorize("hasAnyRole('CAE', 'SERVIDOR')")
    public void exportarPdf(@RequestParam(required = false) Long alunoId,
                            @RequestParam(required = false) Long ambienteId,
                            @RequestParam(required = false) StatusAutorizacao status,
                            HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=autorizacoes.pdf");

        relatorioService.gerarAutorizacoesPdf(response.getOutputStream(), alunoId, ambienteId, status);
    }

    @GetMapping("/exportar/excel")
@PreAuthorize("hasAnyRole('SERVIDOR', 'CAE')")
public void exportarExcel(@RequestParam(required = false) Long alunoId,
                          @RequestParam(required = false) Long ambienteId,
                          @RequestParam(required = false) StatusAutorizacao status,
                          HttpServletResponse response) throws IOException {

    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setHeader("Content-Disposition", "attachment; filename=autorizacoes.xlsx");

    relatorioService.gerarAutorizacoesExcel(response.getOutputStream(), alunoId, ambienteId, status);
}

}
