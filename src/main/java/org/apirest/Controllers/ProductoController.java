package org.apirest.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apirest.modelo.Producto;
import org.apirest.modelo.Mensaje;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static spark.Spark.*;

public class ProductoController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<Long, Producto> productos = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public ProductoController() {
        definirRutas();
    }

    private void definirRutas() {
        // Endpoint para crear un nuevo producto
        // http:localhost:4567/productos
        post("/productos", (request, response) -> {
            response.type("application/json");
            String body = request.body();
            try {
                Producto producto = mapper.readValue(body, Producto.class);
                long id = nextId.getAndIncrement();
                producto.setId((int) id);
                productos.put(id, producto);
                response.status(201); // Código de estado para "Creado"
                return mapper.writeValueAsString(new Mensaje("Producto agregado", producto));
            } catch (Exception e) {
                response.status(400); // Código de estado para "Solicitud Incorrecta"
                return mapper.writeValueAsString(new Mensaje("Error al crear producto", e.getMessage()));
            }
        });

        // Endpoint para obtener todos los productos
        get("/productos", (request, response) -> {
            response.type("application/json");
            return mapper.writeValueAsString(new Mensaje("Lista de productos", new ArrayList<>(productos.values())));
        });

        // Endpoint para obtener un producto por ID
        get("/productos/:id", (request, response) -> {
            response.type("application/json");
            String idStr = request.params(":id");
            try {
                long id = Long.parseLong(idStr);
                Producto producto = productos.get(id);
                if (producto != null) {
                    return mapper.writeValueAsString(new Mensaje("Producto encontrado", producto));
                } else {
                    response.status(404); // Código de estado para "No Encontrado"
                    return mapper.writeValueAsString(new Mensaje("Producto no encontrado", null));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID de producto inválido", idStr));
            }
        });

        // Endpoint para actualizar un producto
        put("/productos/:id", (request, response) -> {
            response.type("application/json");
            String idStr = request.params(":id");
            String body = request.body();
            try {
                long id = Long.parseLong(idStr);
                Producto productoExistente = productos.get(id);
                if (productoExistente != null) {
                    Producto productoActualizado = mapper.readValue(body, Producto.class);
                    productoActualizado.setId((int) id); // Aseguramos que el ID sea el correcto
                    productos.put(id, productoActualizado);
                    return mapper.writeValueAsString(new Mensaje("Producto actualizado", productoActualizado));
                } else {
                    response.status(404);
                    return mapper.writeValueAsString(new Mensaje("Producto no encontrado para actualizar", null));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID de producto inválido", idStr));
            } catch (Exception e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("Error al actualizar producto", e.getMessage()));
            }
        });

        // Endpoint para eliminar un producto
        delete("/productos/:id", (request, response) -> {
            response.type("application/json");
            String idStr = request.params(":id");
            try {
                long id = Long.parseLong(idStr);
                if (productos.containsKey(id)) {
                    productos.remove(id);
                    response.status(204); // Código de estado para "Sin Contenido" (eliminación exitosa)
                    return ""; // No se devuelve cuerpo en una eliminación exitosa con 204
                } else {
                    response.status(404);
                    return mapper.writeValueAsString(new Mensaje("Producto no encontrado para eliminar", null));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID de producto inválido", idStr));
            }
        });
    }

    public static void main(String[] args) {
        new ProductoController();
    }
}