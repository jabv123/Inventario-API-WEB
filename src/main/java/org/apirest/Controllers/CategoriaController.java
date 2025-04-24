package org.apirest.Controllers;

import org.apirest.modelo.Categoria;
import org.apirest.modelo.Mensaje; // Importar Mensaje
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
            List<Map<String, Object>> categoriasJson = new ArrayList<>();
            for (Categoria categoria : categorias) {
                Map<String, Object> categoriaMap = new HashMap<>();
                categoriaMap.put("id", categoria.getId());
                categoriaMap.put("nombre", categoria.getNombre());
                categoriaMap.put("descripcion", categoria.getDescripcion());
                categoriasJson.add(categoriaMap);
            }
            return objectMapper.writeValueAsString(new Mensaje("Categorías obtenidas exitosamente", categoriasJson));
        } catch (Exception e) {
            res.status(500);
            try {
                return objectMapper.writeValueAsString(new Mensaje("Error interno del servidor"));
            } catch (Exception ex) {
                return "Error interno del servidor";
            }
        }
    }

    private String obtenerCategoriaPorId(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            for (Categoria categoria : categorias) {
                if (categoria.getId() == id) {
                    Map<String, Object> categoriaMap = new HashMap<>();
                    categoriaMap.put("id", categoria.getId());
                    categoriaMap.put("nombre", categoria.getNombre());
                    categoriaMap.put("descripcion", categoria.getDescripcion());
                    return objectMapper.writeValueAsString(new Mensaje("Categoría obtenida exitosamente", categoriaMap));
                }
            }
            res.status(404);
            return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada"));
        } catch (Exception e) {
            res.status(400);
            try {
                return objectMapper.writeValueAsString(new Mensaje("Solicitud inválida"));
            } catch (Exception ex) {
                return "Solicitud inválida";
            }
        }
    }

    private String crearCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            Map<String, Object> requestMap = objectMapper.readValue(req.body(), Map.class);
            String nombre = (String) requestMap.get("nombre");
            String descripcion = (String) requestMap.get("descripcion");

            Categoria nuevaCategoria = new Categoria(nombre, descripcion);
            nuevaCategoria.setId(nextId++);
            categorias.add(nuevaCategoria);
            res.status(201);
            
            Map<String, Object> categoriaMap = new HashMap<>();
            categoriaMap.put("id", nuevaCategoria.getId());
            categoriaMap.put("nombre", nuevaCategoria.getNombre());
            categoriaMap.put("descripcion", nuevaCategoria.getDescripcion());
            
            return objectMapper.writeValueAsString(new Mensaje("Categoría creada exitosamente", categoriaMap));

        } catch (Exception e) {
            res.status(400);
            try {
                return objectMapper.writeValueAsString(new Mensaje("Solicitud inválida"));
            } catch (Exception ex) {
                return "Solicitud inválida";
            }
        }
    }

    private String actualizarCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            Map<String, Object> requestMap = objectMapper.readValue(req.body(), Map.class);
            String nombre = (String) requestMap.get("nombre");
            String descripcion = (String) requestMap.get("descripcion");

            for (Categoria categoria : categorias) {
                if (categoria.getId() == id) {
                    if (nombre != null && !nombre.isEmpty()) {
                        categoria.setNombre(nombre);
                    }
                    if (descripcion != null && !descripcion.isEmpty()) {
                        categoria.setDescripcion(descripcion);
                    }
                    Map<String, Object> categoriaMap = new HashMap<>();
                    categoriaMap.put("id", categoria.getId());
                    categoriaMap.put("nombre", categoria.getNombre());
                    categoriaMap.put("descripcion", categoria.getDescripcion());
                    
                    return objectMapper.writeValueAsString(new Mensaje("Categoría actualizada exitosamente", categoriaMap));
                }
            }
            res.status(404);
            return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada"));

        } catch (Exception e) {
            res.status(400);
            try {
                return objectMapper.writeValueAsString(new Mensaje("Solicitud inválida"));
            } catch (Exception ex) {
                return "Solicitud inválida";
            }
        }
    }

    private String eliminarCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            boolean eliminado = categorias.removeIf(categoria -> categoria.getId() == id);
            if (eliminado) {
                return objectMapper.writeValueAsString(new Mensaje("Categoría eliminada exitosamente"));
            } else {
                res.status(404);
                return objectMapper.writeValueAsString(new Mensaje("Categoría no encontrada"));
            }
        } catch (Exception e) {
            res.status(400);
            try {
                return objectMapper.writeValueAsString(new Mensaje("Solicitud inválida"));
            } catch (Exception ex) {
                return "Solicitud inválida";
            }
        }
    }
}