package org.apirest.Controllers;

import org.apirest.modelo.Categoria;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class CategoriaController {

    private static List<Categoria> categorias = new ArrayList<>();
    private static int nextId = 1;

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
        List<Map<String, Object>> categoriasJson = new ArrayList<>();
        for (Categoria categoria : categorias) {
            Map<String, Object> categoriaMap = new HashMap<>();
            categoriaMap.put("id", categoria.getId());
            categoriaMap.put("nombre", categoria.getNombre());
            categoriaMap.put("descripcion", categoria.getDescripcion());
            categoriasJson.add(categoriaMap);
        }
        return new org.json.JSONArray(categoriasJson).toString();
    }

    private String obtenerCategoriaPorId(spark.Request req, spark.Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                Map<String, Object> categoriaMap = new HashMap<>();
                categoriaMap.put("id", categoria.getId());
                categoriaMap.put("nombre", categoria.getNombre());
                categoriaMap.put("descripcion", categoria.getDescripcion());
                return new JSONObject(categoriaMap).toString();
            }
        }
        res.status(404);
        return new JSONObject(Map.of("mensaje", "Categoría no encontrada")).toString();
    }

    private String crearCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            JSONObject jsonObject = new JSONObject(req.body());
            String nombre = jsonObject.getString("nombre");
            String descripcion = jsonObject.getString("descripcion");

            Categoria nuevaCategoria = new Categoria(nombre, descripcion);
            nuevaCategoria.setId(nextId++);
            categorias.add(nuevaCategoria);
            res.status(201);
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Categoría creada exitosamente");
            respuesta.put("id", nuevaCategoria.getId());
            return new JSONObject(respuesta).toString();

        } catch (Exception e) {
            res.status(400);
            return new JSONObject(Map.of("mensaje", "Solicitud inválida")).toString();
        }
    }

    private String actualizarCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        try {
            JSONObject jsonObject = new JSONObject(req.body());
            String nombre = jsonObject.optString("nombre");
            String descripcion = jsonObject.optString("descripcion");

            for (Categoria categoria : categorias) {
                if (categoria.getId() == id) {
                    if (nombre != null && !nombre.isEmpty()) {
                        categoria.setNombre(nombre);
                    }
                    if (descripcion != null && !descripcion.isEmpty()) {
                        categoria.setDescripcion(descripcion);
                    }
                    return new JSONObject(Map.of("mensaje", "Categoría actualizada exitosamente")).toString();
                }
            }
            res.status(404);
            return new JSONObject(Map.of("mensaje", "Categoría no encontrada")).toString();

        } catch (Exception e) {
            res.status(400);
            return new JSONObject(Map.of("mensaje", "Solicitud inválida")).toString();
        }
    }

    private String eliminarCategoria(spark.Request req, spark.Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        categorias.removeIf(categoria -> categoria.getId() == id);
        return new JSONObject(Map.of("mensaje", "Categoría eliminada exitosamente")).toString();
    }

    public static void main(String[] args) {
        new CategoriaController();
    }
}