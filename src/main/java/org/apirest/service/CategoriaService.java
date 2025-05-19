package org.apirest.service;

import java.util.List;

import org.apirest.modelo.Categoria;
import org.apirest.repository.CategoriaRepo;

public class CategoriaService {

    private final CategoriaRepo categoriaRepo;

    public CategoriaService(CategoriaRepo categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    // Listar todas las categorias
    public List<Categoria> getCategorias() {
        return categoriaRepo.getCategorias();
    }

    // Listar por id
    public Categoria getCategoria(int id) {
        return categoriaRepo.getById(id);
    }

    // Crear una categoria
    public Categoria crear(Categoria categoria) {
        return categoriaRepo.crear(categoria);
    }

    // Actualizar una categoria
    public Categoria actualizar(Categoria categoria) {
        return categoriaRepo.actualizar(categoria);
    }

    // Eliminar una categoria
    public boolean eliminar(int id) {
        return categoriaRepo.eliminar(id);
    }

}
