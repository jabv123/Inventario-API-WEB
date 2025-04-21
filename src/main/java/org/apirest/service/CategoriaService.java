package org.apirest.service;

import org.apirest.modelo.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> obtenerTodasCategorias();
    Optional<Categoria> obtenerCategoriaPorId(int id);
    Categoria guardarCategoria(Categoria categoria);
    void eliminarCategoria(int id);
    boolean existeCategoria(int id);
}