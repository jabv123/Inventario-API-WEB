package org.resources.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.resources.modelo.Mensaje;
import org.resources.modelo.Proveedor;

import static spark.Spark.*;

public class ProvedorController {

    private final ObjectMapper mapper = new ObjectMapper();

    public ProvedorController() {
        definirRutas();
    }

    private void definirRutas() {
        // Endpoint para crear un nuevo proveedor
        post("/proveedores", (request, response) -> {
            response.type("application/json");
            String body = request.body();
            try {
                Proveedor proveedor = mapper.readValue(body, Proveedor.class);
                proveedor.setId(ProveedorBD.autoId); // Suponiendo un ID autoincremental
                ProvedorController.autoId++;
                ProveedorBD.proveedores.add(proveedor);
                return mapper.writeValueAsString(new Mensaje("Proveedor agregado", proveedor));
            } catch (Exception e) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("Error al crear proveedor", e.getMessage()));
            }
        });

        // Endpoint para obtener todos los proveedores
        get("/proveedores", (request, response) -> {
            response.type("application/json");
            return mapper.writeValueAsString(new Mensaje("Lista de proveedores", ProvedorController.proveedores));
        });

        // Endpoint para obtener un proveedor por ID
        get("/proveedores/:id", (request, response) -> {
            // ... lógica para obtener un proveedor por ID ...
        });

        // Endpoint para actualizar un proveedor
        put("/proveedores/:id", (request, response) -> {
            // ... lógica para actualizar un proveedor ...
        });

        // Endpoint para eliminar un proveedor
        delete("/proveedores/:id", (request, response) -> {
            // ... lógica para eliminar un proveedor ...
        });
    }
}