package org.apirest.Controllers;

import org.apirest.modelo.Categoria;
import org.apirest.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Optional;

public class CategoriaController {

    private CategoriaService categoriaService = null;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public Route obtenerTodasCategorias = (Request request, Response response) -> {
        response.type("application/json");
        List<Categoria> categorias = categoriaService.obtenerTodasCategorias();
        return objectMapper.writeValueAsString(categorias);
    };

    public Route obtenerCategoriaPorId = (Request request, Response response) -> {
        response.type("application/json");
        int id = Integer.parseInt(request.params(":id"));
        Optional<Categoria> categoriaOptional = categoriaService.obtenerCategoriaPorId(id);
        if (categoriaOptional.isPresent()) {
            return objectMapper.writeValueAsString(categoriaOptional.get());
        } else {
            response.status(404);
            return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada"));
        }
    };

    public Route guardarCategoria = (Request request, Response response) -> {
        response.type("application/json");
        try {
            Categoria nuevaCategoria = objectMapper.readValue(request.body(), Categoria.class);
            Categoria categoriaCreada = categoriaService.guardarCategoria(nuevaCategoria);
            response.status(201); // Created
            return objectMapper.writeValueAsString(categoriaCreada);
        } catch (Exception e) {
            response.status(400); // Bad Request
            return objectMapper.writeValueAsString(new Mensaje("Error al crear la categoría: " + e.getMessage()));
        }
    };

    public Route actualizarCategoria = (Request request, Response response) -> {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            Categoria categoriaActualizada = objectMapper.readValue(request.body(), Categoria.class);
            categoriaActualizada.setId(id); // Asegurar que el ID se mantiene
            Optional<Categoria> categoriaExistente = categoriaService.obtenerCategoriaPorId(id);
            if (categoriaExistente.isPresent()) {
                Categoria categoriaGuardada = categoriaService.guardarCategoria(categoriaActualizada);
                return objectMapper.writeValueAsString(categoriaGuardada);
            } else {
                response.status(404);
                return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada para actualizar"));
            }
        } catch (Exception e) {
            response.status(400); // Bad Request
            return objectMapper.writeValueAsString(new Mensaje("Error al actualizar la categoría: " + e.getMessage()));
        }
    };

    public Route eliminarCategoria = (Request request, Response response) -> {
        response.type("application/json");
        int id = Integer.parseInt(request.params(":id"));
        if (categoriaService.existeCategoria(id)) {
            categoriaService.eliminarCategoria(id);
            response.status(204); // No Content
            return "";
        } else {
            response.status(404);
            return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada para eliminar"));
        }
    };

    private static class Mensaje {
        private final String mensaje;

        public Mensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}