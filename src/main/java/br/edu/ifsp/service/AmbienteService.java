package br.edu.ifsp.service;

import br.edu.ifsp.domain.Ambiente;
import br.edu.ifsp.dto.AmbienteDTO;
import br.edu.ifsp.repository.AmbienteRepository;
import br.edu.ifsp.service.shared.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmbienteService extends CrudService<Ambiente, Long> {

    private final AmbienteRepository repository;

    @Override
    protected JpaRepository<Ambiente, Long> getRepository() {
        return repository;
    }

    public Ambiente salvar(AmbienteDTO dto) {
        Ambiente ambiente = new Ambiente();
        ambiente.setNome(dto.getNome());
        ambiente.setLocalizacao(dto.getLocalizacao());
        return repository.save(ambiente);
    }

    public Ambiente atualizar(Long id, AmbienteDTO dto) {
        Ambiente ambiente = buscarPorId(id); // herdado
        ambiente.setNome(dto.getNome());
        ambiente.setLocalizacao(dto.getLocalizacao());
        return repository.save(ambiente);
    }

    public List<Ambiente> listarDisponiveis() {
        return repository.findByDisponivelTrue();
    }
}
