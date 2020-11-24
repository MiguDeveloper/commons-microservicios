package pe.tuna.commonsmicroservicios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CommonServiceImpl<E, R extends JpaRepository<E,Long>> implements ICommonService<E> {

    @Autowired
    protected R repository;

    @Override
    @Transactional(readOnly = true)
    public List findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public E findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
