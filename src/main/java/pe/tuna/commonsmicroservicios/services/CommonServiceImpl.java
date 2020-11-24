package pe.tuna.commonsmicroservicios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class CommonServiceImpl<E, R extends JpaRepository<E,Long>> implements ICommonService<E> {

    @Autowired
    private R repository;

    @Override
    public List findAll() {
        return repository.findAll();
    }

    @Override
    public E findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
