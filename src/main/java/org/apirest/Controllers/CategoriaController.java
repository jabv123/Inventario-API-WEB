package org.apirest.Controllers;

import org.apirest.modelo.Categoria;
import org.apirest.modelo.Mensaje;
import org.apirest.service.CategoriaService; // Asegúrate de tener este servicio
import com.fasterxml.jackson.databind.ObjectMapper;

import static spark.Spark.*;

public class CategoriaController {

    private final CategoriaService categoriaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
        rutas();
    }

    private void rutas() {
        path("/api/categorias", () -> {
            get("", (req, res) -> {
                res.type("application/json");
                return objectMapper.writeValueAsString(categoriaService.obtenerTodasLasCategorias());
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
                if (categoria != null) {
                    return objectMapper.writeValueAsString(categoria);
                } else {
                    res.status(404);
                    return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada"));
                }
            });

            post("", (req, res) -> {
                res.type("application/json");
                try {
                    Categoria nuevaCategoria = objectMapper.readValue(req.body(), Categoria.class);
                    Categoria categoriaCreada = categoriaService.guardarCategoria(nuevaCategoria);
                    res.status(201);
                    return objectMapper.writeValueAsString(categoriaCreada);
                } catch (Exception e) {
                    res.status(400);
                    return objectMapper.writeValueAsString(new Mensaje("Error al crear la categoría: " + e.getMessage()));
                }
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                Categoria categoriaExistente = categoriaService.obtenerCategoriaPorId(id);
                if (categoriaExistente != null) {
                    try {
                        Categoria categoriaActualizada = objectMapper.readValue(req.body(), Categoria.class);
                        categoriaActualizada.setId(id);
                        Categoria categoriaGuardada = categoriaService.guardarCategoria(categoriaActualizada);
                        return objectMapper.writeValueAsString(categoriaGuardada);
                    } catch (Exception e) {
                        res.status(400);
                        return objectMapper.writeValueAsString(new Mensaje("Error al actualizar la categoría: " + e.getMessage()));
                    }
                } else {
                    res.status(404);
                    return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada"));
                }
            });

            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                boolean eliminada = categoriaService.eliminarCategoria(id);
                if (eliminada) {
                    res.status(204);
                    return "";
                } else {
                    res.status(404);
                    return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada"));
                }
            });
        });
    }
}