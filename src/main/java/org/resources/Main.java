package org.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.resources.modelo.Mensaje;
import org.resources.modelo.Producto;
import org.resources.modelo.ProductosBD;

import static spark.Spark.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main { 
    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        get("/", (request, response) -> {
            response.type("application/json"); // Establece el tipo de contenido
            return "{\"message\": \"Hello, World!\"}"; // Devuelve un JSON
        });

        // http://localhost:4567/productos
        post("/productos", (request, response) -> {
            response.type("application/json");
            String body = request.body();
            Producto product = mapper.readValue(body, Producto.class);

            product.setId(ProductosBD.autoId);
            ProductosBD.autoId++;

            ProductosBD.productos.add(product);
            Mensaje mensaje = new Mensaje("Producto agregado", body);

            return mapper.writeValueAsString(mensaje);
        });

        get("/productos", (request, response) -> {
            response.type("application/json");
            return mapper.writeValueAsString(ProductosBD.productos);
        });

        get("/productos/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            if (id == null) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID no proporcionado", ""));
            }
            Producto product = null;
            for (Producto p : ProductosBD.productos) {
                if (p.getId() == Integer.parseInt(id)) {
                    product = p;
                    break;
                }
            }
            if (product != null) {
                return mapper.writeValueAsString(product);
            } else {
                response.status(404);
                return mapper.writeValueAsString(new Mensaje("Producto no encontrado", ""));
            }
        });

        put("/productos/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            if (id == null) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID no proporcionado", ""));
            }
            String body = request.body();
            Producto updatedProduct = mapper.readValue(body, Producto.class);
            updatedProduct.setId(Integer.parseInt(id));

            for (int i = 0; i < ProductosBD.productos.size(); i++) {
                if (ProductosBD.productos.get(i).getId() == Integer.parseInt(id)) {
                    ProductosBD.productos.set(i, updatedProduct);
                    return mapper.writeValueAsString(new Mensaje("Producto actualizado", ""));
                }
            }
            response.status(404);
            return mapper.writeValueAsString(new Mensaje("Producto no encontrado", ""));
        });

        delete("/productos/:id", (request, response) -> {
            response.type("application/json");
            String id = request.params(":id");
            if (id == null) {
                response.status(400);
                return mapper.writeValueAsString(new Mensaje("ID no proporcionado", ""));
            }
            for (int i = 0; i < ProductosBD.productos.size(); i++) {
                if (ProductosBD.productos.get(i).getId() == Integer.parseInt(id)) {
                    ProductosBD.productos.remove(i);
                    return mapper.writeValueAsString(new Mensaje("Producto eliminado", ""));
                }
            }
            response.status(404);
            return mapper.writeValueAsString(new Mensaje("Producto no encontrado", ""));
        });
    }
}