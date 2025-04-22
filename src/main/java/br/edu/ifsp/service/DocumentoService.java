package br.edu.ifsp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifsp.domain.Aluno;
import br.edu.ifsp.domain.Documento;
import br.edu.ifsp.repository.AlunoRepository;
import br.edu.ifsp.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository repository;
    private final AlunoRepository alunoRepository;

    private final Path diretorioUpload = Paths.get("uploads");

    public Documento salvarDocumento(Long alunoId, MultipartFile file) throws IOException {
        if (!Files.exists(diretorioUpload)) {
            Files.createDirectories(diretorioUpload);
        }

        Aluno aluno = alunoRepository.findById(alunoId).orElseThrow();

        String nomeSalvo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path caminhoCompleto = diretorioUpload.resolve(nomeSalvo);
        Files.copy(file.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

        Documento doc = new Documento();
        doc.setAluno(aluno);
        doc.setNomeOriginal(file.getOriginalFilename());
        doc.setCaminhoArquivo(caminhoCompleto.toString());

        return repository.save(doc);
    }

    public List<Documento> listarPorAluno(Long alunoId) {
        return repository.findByAlunoId(alunoId);
    }
}
