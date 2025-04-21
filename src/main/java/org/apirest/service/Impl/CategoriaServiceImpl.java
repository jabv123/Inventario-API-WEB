package org.apirest.service.Impl;

import org.apirest.modelo.Categoria;
import org.apirest.repository.CategoriaRepo;
import org.apirest.service.CategoriaService;

import java.util.List;
import java.util.Optional;

public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepo categoriaRepo;

    public CategoriaServiceImpl(CategoriaRepo categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    @Override
    public List<Categoria> obtenerTodasCategorias() {
        return categoriaRepo.findAll();
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorId(int id) {
        return categoriaRepo.findById(id);
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepo.save(categoria);
    }

    @Override
    public void eliminarCategoria(int id) {
        categoriaRepo.deleteById(id);
    }

    @Override
    public boolean existeCategoria(int id) {
        return categoriaRepo.existsById(id);
    }
}