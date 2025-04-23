package br.edu.ifsp.service.shared;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CrudService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public List<T> listar() {
        return getRepository().findAll();
    }

    public T buscarPorId(ID id) {
        return getRepository().findById(id)
            .orElseThrow(() -> new RuntimeException("Não encontrado"));
    }

    public void deletar(ID id) {
        getRepository().deleteById(id);
    }
}
