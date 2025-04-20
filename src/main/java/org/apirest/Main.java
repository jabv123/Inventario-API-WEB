package org.apirest;

import com.fasterxml.jackson.databind.ObjectMapper;
import static spark.Spark.*;

import org.apirest.Controllers.ProductoController;
import org.apirest.Controllers.ProveedorController;

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