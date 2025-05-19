package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.Categoria;

public class CategoriaRepo {

    private final List<Categoria> categorias = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    // Listar todas las categorias
    public List<Categoria> getCategorias() {
        return categorias;
    }

    // Listar por id
    public Categoria getById(int id) {
        return categorias.stream().filter(categoria -> categoria.getId() == id).findFirst().orElse(null);
    }

    // Crear una categoria
    public Categoria crear(Categoria categoria) {
        categoria.setId(id.getAndIncrement());
        categorias.add(categoria);
        return categoria;
    }

    // Actualizar una categoria
    public Categoria actualizar(Categoria categoria) {
        Categoria categoriaExistente = getById(categoria.getId());
        if (categoriaExistente != null) {
            categoriaExistente.setNombre(categoria.getNombre());
            categoriaExistente.setDescripcion(categoria.getDescripcion());
            return categoriaExistente;
        }
        return null;
    }

    // Eliminar una categoria
    public boolean eliminar(int id) {
        Categoria categoria = getById(id);
        if (categoria != null) {
            categorias.remove(categoria);
            return true;
        }
        return false;
    }

}
