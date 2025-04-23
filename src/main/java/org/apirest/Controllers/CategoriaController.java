package org.apirest.Controllers;

import org.apirest.modelo.Categoria;
import org.apirest.modelo.Mensaje; // Importar Mensaje
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map; // Importar Map

import com.fasterxml.jackson.databind.ObjectMapper; // Importar ObjectMapper

public class CategoriaController {

    private static List<Categoria> categorias = new ArrayList<>();
    private static int nextId = 1;
    private static ObjectMapper objectMapper = new ObjectMapper(); // Instanciar ObjectMapper

    public CategoriaController() {
        // Definición de las rutas
        path("/categorias", () -> {
            get("", (req, res) -> obtenerTodasLasCategorias(req, res));
            get("/:id", (req, res) -> obtenerCategoriaPorId(req, res));
            post("", (req, res) -> crearCategoria(req, res));
            put("/:id", (req, res) -> actualizarCategoria(req, res));
            delete("/:id", (req, res) -> eliminarCategoria(req, res));
        });
    }

    private String obtenerTodasLasCategorias(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            // Serializar directamente la lista de categorías
            return objectMapper.writeValueAsString(categorias);
        } catch (Exception e) {
            res.status(500);
            // Usar Mensaje para el error
            return writeValueAsString(new Mensaje("Error al obtener categorías"));
        }
    }

    private String obtenerCategoriaPorId(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            for (Categoria categoria : categorias) {
                if (categoria.getId() == id) {
                    // Serializar el objeto Categoria encontrado
                    return objectMapper.writeValueAsString(categoria);
                }
            }
            res.status(404);
            // Usar Mensaje para categoría no encontrada
            return writeValueAsString(new Mensaje("Categoría no encontrada"));
        } catch (NumberFormatException e) {
            res.status(400);
            return writeValueAsString(new Mensaje("ID inválido"));
        } catch (Exception e) {
            res.status(500);
            return writeValueAsString(new Mensaje("Error al obtener categoría"));
        }
    }

    private String crearCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            // Deserializar el cuerpo de la solicitud a un objeto Categoria
            Categoria nuevaCategoria = objectMapper.readValue(req.body(), Categoria.class);

            // Validar si los campos necesarios están presentes (opcional, depende de la lógica de negocio)
            if (nuevaCategoria.getNombre() == null || nuevaCategoria.getNombre().isEmpty()) {
                 res.status(400);
                 return writeValueAsString(new Mensaje("El nombre de la categoría es obligatorio"));
            }

            nuevaCategoria.setId(nextId++);
            categorias.add(nuevaCategoria);
            res.status(201);
            // Usar Mensaje para la respuesta de éxito, incluyendo el ID
            return writeValueAsString(new Mensaje("Categoría creada exitosamente", Map.of("id", nuevaCategoria.getId())));

        } catch (Exception e) {
            res.status(400);
            // Usar Mensaje para solicitud inválida
            return writeValueAsString(new Mensaje("Solicitud inválida: " + e.getMessage()));
        }
    }

    private String actualizarCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            // Deserializar el cuerpo de la solicitud a un objeto Categoria temporal
            Categoria datosActualizacion = objectMapper.readValue(req.body(), Categoria.class);

            for (Categoria categoria : categorias) {
                if (categoria.getId() == id) {
                    // Actualizar campos si se proporcionan en la solicitud
                    if (datosActualizacion.getNombre() != null && !datosActualizacion.getNombre().isEmpty()) {
                        categoria.setNombre(datosActualizacion.getNombre());
                    }
                    if (datosActualizacion.getDescripcion() != null) { // Permitir descripción vacía si se desea
                        categoria.setDescripcion(datosActualizacion.getDescripcion());
                    }
                    // Usar Mensaje para la respuesta de éxito
                    return writeValueAsString(new Mensaje("Categoría actualizada exitosamente"));
                }
            }
            res.status(404);
            // Usar Mensaje para categoría no encontrada
            return writeValueAsString(new Mensaje("Categoría no encontrada"));

        } catch (NumberFormatException e) {
            res.status(400);
            return writeValueAsString(new Mensaje("ID inválido"));
        } catch (Exception e) {
            res.status(400);
            // Usar Mensaje para solicitud inválida
            return writeValueAsString(new Mensaje("Solicitud inválida: " + e.getMessage()));
        }
    }

    private String eliminarCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            boolean removed = categorias.removeIf(categoria -> categoria.getId() == id);
            if (removed) {
                // Usar Mensaje para la respuesta de éxito
                return writeValueAsString(new Mensaje("Categoría eliminada exitosamente"));
            } else {
                res.status(404);
                return writeValueAsString(new Mensaje("Categoría no encontrada"));
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return writeValueAsString(new Mensaje("ID inválido"));
        } catch (Exception e) {
             res.status(500);
             return writeValueAsString(new Mensaje("Error al eliminar categoría"));
        }
    }

    // Método auxiliar para manejar la serialización y excepciones
    private String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            // Loggear el error sería una buena práctica aquí
            // Crear un objeto Mensaje para el error y serializarlo
            try {
                return objectMapper.writeValueAsString(new Mensaje("Error interno al serializar la respuesta"));
            } catch (Exception innerEx) {
                // Fallback muy básico si incluso la serialización del mensaje de error falla
                return "{\"error\":\"Error interno grave\"}";
            }
        }
    }
}