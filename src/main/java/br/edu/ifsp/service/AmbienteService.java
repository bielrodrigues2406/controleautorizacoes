package br.edu.ifsp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifsp.domain.Ambiente;
import br.edu.ifsp.dto.AmbienteDTO;
import br.edu.ifsp.repository.AmbienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmbienteService {

    private final AmbienteRepository repository;

    public Ambiente salvar(AmbienteDTO dto) {
        Ambiente ambiente = new Ambiente(null, null, null, null);
        return repository.save(ambiente);
    }

    public List<Ambiente> listar() {
        return repository.findAll();
    }

    public List<Ambiente> listarDisponiveis() {
        return repository.findByDisponivelTrue();
    }

    public Ambiente buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Ambiente não encontrado"));
    }

    public Ambiente atualizar(Long id, AmbienteDTO dto) {
        Ambiente ambiente = buscarPorId(id);
        ambiente.setNome(dto.getNome());
        ambiente.setLocalizacao(dto.getLocalizacao());
        return repository.save(ambiente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
