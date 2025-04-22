package br.edu.ifsp.service;

import br.edu.ifsp.domain.Autorizacao;
import br.edu.ifsp.enums.StatusAutorizacao;
import br.edu.ifsp.repository.AutorizacaoRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final AutorizacaoRepository autorizacaoRepository;

    public void gerarAutorizacoesPdf(OutputStream out, Long alunoId, Long ambienteId, StatusAutorizacao status) {
        try {
            // Gera consulta paginada sem limite usando PageRequest
            List<Autorizacao> lista = autorizacaoRepository
                    .filtrarAutorizacoes(alunoId, ambienteId, status, PageRequest.of(0, Integer.MAX_VALUE))
                    .getContent();

            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font tableHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font tableBody = FontFactory.getFont(FontFactory.HELVETICA, 11);

            document.add(new Paragraph("Relatório de Autorizações", titleFont));
            document.add(new Paragraph(" ")); // espaço

            PdfPTable tabela = new PdfPTable(6);
            tabela.setWidthPercentage(100);
            tabela.setWidths(new int[]{1, 3, 3, 2, 3, 3});

            // Cabeçalhos
            addCelula(tabela, "ID", tableHeader);
            addCelula(tabela, "Aluno", tableHeader);
            addCelula(tabela, "Ambiente", tableHeader);
            addCelula(tabela, "Status", tableHeader);
            addCelula(tabela, "Início", tableHeader);
            addCelula(tabela, "Fim", tableHeader);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Preenche tabela
            for (Autorizacao aut : lista) {
                addCelula(tabela, String.valueOf(aut.getId()), tableBody);
                addCelula(tabela, aut.getAluno().getNome(), tableBody);
                addCelula(tabela, aut.getAmbiente().getNome(), tableBody);
                addCelula(tabela, aut.getStatus().name(), tableBody);
                addCelula(tabela, aut.getDataInicio().format(formatter), tableBody);
                addCelula(tabela, aut.getDataFim().format(formatter), tableBody);
            }

            document.add(tabela);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }

    private void addCelula(PdfPTable table, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(5);
        table.addCell(cell);
    }
}
