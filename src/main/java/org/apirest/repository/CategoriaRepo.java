package org.apirest.repository;

import org.apirest.modelo.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepo {
    List<Categoria> findAll();
    Optional<Categoria> findById(int id);
    Categoria save(Categoria categoria);
    void deleteById(int id);
    boolean existsById(int id);
}