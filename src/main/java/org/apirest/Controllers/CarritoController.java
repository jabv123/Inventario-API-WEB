package org.apirest.Controllers;

import org.apirest.modelo.Carrito;
import org.apirest.Util.Mensaje;
import org.apirest.service.CarritoService;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class CarritoController {

    private final CarritoService carritoService;
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    public void rutasCarrito() {
        path("/api/carrito", () -> {
            get(this::obtenerTodos);
            post(this::crearCarrito);
            path("{id}", () -> {
                get(this::obtenerPorId);
                //put(this::update);
                //delete(this::deleteById);
            });
        });

    }

    private void obtenerTodos(Context ctx) {
        List<Carrito> carritos = carritoService.getAllCarritos();
        if(carritos.isEmpty()){
            throw new NotFoundResponse("No hay carritos disponibles");
        }
        ctx.status(200).json(new Mensaje("Lista de carritos", carritos));

    }

    private void crearCarrito(Context ctx) {
            Carrito carrito = ctx.bodyAsClass(Carrito.class);
            Carrito nuevoCarrito = carritoService.crearCarrito(carrito);
            ctx.status(201).json(new Mensaje("Carrito creado correctamente", nuevoCarrito));
    }

    private void obtenerPorId(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        //Obtener carrito por id
        Carrito carrito = carritoService.getCarritoById(id);
        //Comprobar si existe
        if (carrito == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }
        ctx.status(200).json(new Mensaje("Carrito encontrado", carrito));
    }
}
