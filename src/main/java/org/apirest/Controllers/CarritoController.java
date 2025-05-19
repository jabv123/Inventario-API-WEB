package org.apirest.Controllers;

import org.apirest.modelo.Carrito;
import org.apirest.Util.Mensaje;
import org.apirest.service.CarritoService;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;

import io.javalin.http.Context;

public class CarritoController {

    private final CarritoService carritoService;
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    public void rutasCarrito() {
        path("/api/carrito", () -> {
            get(this::obtenerTodos);
            post(this::crearCarrito);
            //path("{id}", () -> {
                //get(this::getById);
                //put(this::update);
                //delete(this::deleteById);
            //});
        });

    }

    private void obtenerTodos(Context ctx) {
        try {
            List<Carrito> carritos = carritoService.getAllCarritos();
            if (carritos.isEmpty()) {
                ctx.status(404).json(new Mensaje("No hay carritos registrados", carritos));
            } else {
                ctx.status(200).json(carritos);
            }
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al listar carritos: " + e.getMessage(), null));
        }
    }

    private void crearCarrito(Context ctx) {
        try {
            Carrito carrito = ctx.bodyAsClass(Carrito.class);
            Carrito nuevoCarrito = carritoService.crearCarrito(carrito);
            ctx.status(201).json(new Mensaje("Carrito creado correctamente", nuevoCarrito));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(new Mensaje("Error en los datos de entrada: " + e.getMessage(), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al crear carrito: " + e.getMessage(), null));
        }
    }


}
