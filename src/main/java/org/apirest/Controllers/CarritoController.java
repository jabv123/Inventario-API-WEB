package org.apirest.Controllers;

import org.apirest.modelo.Carrito;
import org.apirest.modelo.ItemCarrito;
import org.apirest.Util.Mensaje;
import org.apirest.service.CarritoService;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;
import java.util.Optional;

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
            // Operaciones por idCliente
            path("/cliente/{idCliente}", () -> {
                get(this::obtenerPorIdCliente);
                delete(this::eliminarPorIdCliente);
            });
            path("/{id}", () -> {
                get(this::obtenerPorId);
                put(this::actualizarCarrito);
                delete(this::eliminarCarrito);
            });
            path("/total/{id}", () -> {
                get(this::obtenerTotalDelCarrito);
            });
            path("/{id}/item", () -> {
                post(this::agregarItem);
                path("/{itemId}", () -> {
                    put(this::actualizarCantidadItem);
                    delete(this::eliminarItem);
                });
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

    private void obtenerPorIdCliente(Context ctx) {
        int idCliente = Integer.parseInt(ctx.pathParam("idCliente"));
        //Obtener carrito por idCliente
        Carrito carrito = carritoService.getCarritoByIdCliente(idCliente);
        //Comprobar si existe
        if (carrito == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }
        ctx.status(200).json(new Mensaje("Carrito encontrado", carrito));
    }

    private void eliminarPorIdCliente(Context ctx) {
        int idCliente = Integer.parseInt(ctx.pathParam("idCliente"));
        //Comprobar si existe
        if (carritoService.getCarritoByIdCliente(idCliente) == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }
        carritoService.eliminarCarritoPorIdCliente(idCliente);
        ctx.status(200).json(new Mensaje("Carrito eliminado correctamente", null));
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

    private void obtenerTotalDelCarrito(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Carrito carrito = carritoService.getCarritoById(id);
        //Comprobar si existe
        if (carrito == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }
        double total = carritoService.obtenerTotalDelCarrito(carrito.getId());
        ctx.status(200).json(new Mensaje("Total del carrito", total));
    }

    private void actualizarCarrito(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Carrito carrito = ctx.bodyAsClass(Carrito.class);
        //Comprobar si existe
        if (carritoService.getCarritoById(id) == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }
        Carrito carritoActualizado = carritoService.actualizarCarrito(id, carrito);
        ctx.status(200).json(new Mensaje("Carrito actualizado correctamente", carritoActualizado));
    }

    private void eliminarCarrito(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        //Comprobar si existe
        if (carritoService.getCarritoById(id) == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }
        carritoService.eliminarCarrito(id);
        ctx.status(200).json(new Mensaje("Carrito eliminado correctamente", null));
    }

    //Agregar item al carrito
    private void agregarItem(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Carrito carrito = carritoService.getCarritoById(id);
        //Comprobar si existe
        if (carrito == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }
        ItemCarrito item = ctx.bodyAsClass(ItemCarrito.class);
        carritoService.agregarItemAlCarrito(id, item);
        ctx.status(200).json(new Mensaje("Item agregado correctamente", carrito));
    }

    //Actualizar item del carrito
    private void actualizarCantidadItem(Context ctx) {
        int idCarrito = Integer.parseInt(ctx.pathParam("id"));
        int idItem = Integer.parseInt(ctx.pathParam("itemId"));
        ItemCarrito item = ctx.bodyAsClass(ItemCarrito.class);

        // Validaciones
        Carrito carrito = carritoService.getCarritoById(idCarrito);
        if (carrito == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }

        if (carrito.getItems().stream().noneMatch(i -> i.getId() == idItem)) {
            throw new NotFoundResponse("Item con ID " + idItem + " no encontrado en el carrito.");
        }

        Optional<ItemCarrito> itemActualizadoOpt = carritoService.actualizarCantidadItem(idCarrito, idItem, item.getCantidad());

        if (itemActualizadoOpt.isPresent()) {
            ctx.status(200).json(new Mensaje("Item actualizado correctamente", itemActualizadoOpt.get()));
        } else {
            throw new NotFoundResponse("No se pudo actualizar el item. Item con ID " + idItem + " no encontrado por el servicio o carrito no encontrado.");
        }
    }

    //Eliminar item del carrito
    private void eliminarItem(Context ctx) {
        int idCarrito = Integer.parseInt(ctx.pathParam("id"));
        int idItem = Integer.parseInt(ctx.pathParam("itemId"));

        //Comprobar si existe el carrito
        if (carritoService.getCarritoById(idCarrito) == null) {
            throw new NotFoundResponse("Carrito no encontrado");
        }

        //Comprobar si existe el item
        if (carritoService.getCarritoById(idCarrito).getItems().stream().anyMatch(i -> i.getId() == idItem)) {
            ItemCarrito item = carritoService.getCarritoById(idCarrito).getItems().stream().filter(i -> i.getId() == idItem).findFirst().orElse(null);
            carritoService.eliminarItemDelCarrito(idCarrito, item);
            ctx.status(200).json(new Mensaje("Item eliminado correctamente", item));
        } else {
            throw new NotFoundResponse("Item no encontrado");
        }
    }
}
