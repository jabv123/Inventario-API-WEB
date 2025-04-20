package org.apirest.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apirest.modelo.Mensaje;
import org.apirest.modelo.Proveedor;

import static spark.Spark.*;

public class ProveedorController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<Long, Proveedor> proveedores = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public ProveedorController() {
        definirRutas();
    }

    private void definirRutas() {
        // Endpoint para crear un nuevo proveedor
        // http:localhost:4567/proveedores
        post("/proveedores", (request, response) -> {
            response.type("application/json");
            String body = request.body();
            try {
                Proveedor proveedor = mapper.readValue(body, Proveedor.class);
                long id = nextId.getAndIncrement();
                proveedor.setId(id);
                proveedores.put(id, proveedor);
                response.status(201); // Código de estado para "Creado"
                return mapper.writeValueAsString(new Mensaje("Proveedor agregado", proveedor));
            } catch (Exception e) {
                response.status(400); // Código de estado para "Solicitud Incorrecta"
                return mapper.writeValueAsString(new Mensaje("Error al crear proveedor", e.getMessage()));
            }
        });

        // Endpoint para obtener todos los proveedores
        get("/proveedores", (request, response) -> {
            response.type("application/json");
            return mapper.writeValueAsString(new Mensaje("Lista de proveedores", new ArrayList<>(proveedores.values())));
        });

        // Endpoint para obtener un proveedor por ID
        get("/proveedores/:id", (request, response) -> {
            response.type("application/json");
            String idStr = request.params(":id");
            try {
                long id = Long.parseLong(idStr);
                Proveedor proveedor = proveedores.get(id);
                if (proveedor != null) {
                    return mapper.writeValueAsString(new Mensaje("Proveedor encontrado", proveedor));
                } else {
                    response.status(404); // Código de estado para "No Encontrado"
                    return mapper.writeValueAsString(new Mensaje("Proveedor no encontrado", null));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID de proveedor inválido", idStr));
            }
        });

        // Endpoint para actualizar un proveedor
        put("/proveedores/:id", (request, response) -> {
            response.type("application/json");
            String idStr = request.params(":id");
            String body = request.body();
            try {
                long id = Long.parseLong(idStr);
                Proveedor proveedorExistente = proveedores.get(id);
                if (proveedorExistente != null) {
                    Proveedor proveedorActualizado = mapper.readValue(body, Proveedor.class);
                    proveedorActualizado.setId(id); // Aseguramos que el ID sea el correcto
                    proveedores.put(id, proveedorActualizado);
                    return mapper.writeValueAsString(new Mensaje("Proveedor actualizado", proveedorActualizado));
                } else {
                    response.status(404);
                    return mapper.writeValueAsString(new Mensaje("Proveedor no encontrado para actualizar", null));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID de proveedor inválido", idStr));
            } catch (Exception e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("Error al actualizar proveedor", e.getMessage()));
            }
        });

        // Endpoint para eliminar un proveedor
        delete("/proveedores/:id", (request, response) -> {
            response.type("application/json");
            String idStr = request.params(":id");
            try {
                long id = Long.parseLong(idStr);
                if (proveedores.containsKey(id)) {
                    proveedores.remove(id);
                    response.status(204); // Código de estado para "Sin Contenido" (eliminación exitosa)
                    return ""; // No se devuelve cuerpo en una eliminación exitosa con 204
                } else {
                    response.status(404);
                    return mapper.writeValueAsString(new Mensaje("Proveedor no encontrado para eliminar", null));
                }
            } catch (NumberFormatException e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID de proveedor inválido", idStr));
            }
        });
    }
}