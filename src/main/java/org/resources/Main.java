package org.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import static spark.Spark.*;
import org.resources.Controllers.ProveedorController;
import org.resources.Controllers.ProductoController;

public class Main {
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        get("/", (request, response) -> {
            response.type("application/json");
            return "{\"message\": \"Hello, World!\"}";
        });

        // Inicializar los controladores
        new ProveedorController();
        new ProductoController();

        System.out.println("Servidor Spark iniciado y escuchando en el puerto 4567...");
    }
}