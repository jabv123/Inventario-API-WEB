package org.apirest.repository.Impl;

import org.apirest.modelo.Categoria;
import org.apirest.repository.CategoriaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collections;

public class CategoriaRepoImpl implements CategoriaRepo {

    private final List<Categoria> categorias = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @Override
    public List<Categoria> findAll() {
        return new ArrayList<>(categorias);
    }

    @Override
    public Optional<Categoria> findById(int id) {
        return categorias.stream()
                .filter(categoria -> categoria.getId() == id)
                .findFirst();
    }

    @Override
    public Categoria save(@org.jetbrains.annotations.NotNull Categoria categoria) {
        if (categoria.getId() == 0) {
            categoria.setId(idCounter.getAndIncrement());
            categorias.add(categoria);
        } else {
            categorias.stream()
                    .filter(c -> c.getId() == categoria.getId())
                    .findFirst()
                    .ifPresent(existingCategoria -> {
                        existingCategoria.setNombre(categoria.getNombre());
                        existingCategoria.setDescripcion(categoria.getDescripcion());
                    });
        }
        return categoria;
    }

    @Override
    public void deleteById(int id) {
        categorias.removeIf(categoria -> categoria.getId() == id);
    }

    @Override
    public boolean existsById(int id) {
        return categorias.stream().anyMatch(categoria -> categoria.getId() == id);
    }
}