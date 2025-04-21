package org.apirest.service;

import org.apirest.modelo.Categoria;
import org.apirest.repository.CategoriaRepo;

import java.util.List;
import java.util.Optional;

public class CategoriaService {

    private final CategoriaRepo categoriaRepo;

    public CategoriaService(CategoriaRepo categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepo.findAll();
    }

    public Categoria obtenerCategoriaPorId(int id) {
        Optional<Categoria> categoriaOptional = categoriaRepo.findById(id);
        return categoriaOptional.orElse(null);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepo.save(categoria);
    }

    public boolean eliminarCategoria(int id) {
        if (categoriaRepo.existsById(id)) {
            categoriaRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existeCategoria(int id) {
        return categoriaRepo.existsById(id);
    }

    // Puedes añadir más métodos de lógica de negocio relacionados con las categorías aquí
}