package org.apirest.Controllers;

import org.apirest.Util.Mensaje;
import org.apirest.modelo.Venta;
import org.apirest.service.VentaService;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import static io.javalin.apibuilder.ApiBuilder.*;

public class VentaController {

    private final VentaService ventaService;
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    public void rutasVentas(){
        path("/api/ventas", () -> {
            post(this::realizarVenta);
            get(this::listarVentas);
            path("/{id}", () -> {
                put(this::actualizarVenta);
            });
        });
    }

    private void realizarVenta(Context ctx) {
        Venta venta = ctx.bodyAsClass(Venta.class);
        ventaService.realizarVenta(venta);
        ctx.status(201).json(new Mensaje("Venta realizada correctamente", venta));
    }

    private void actualizarVenta(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Venta ventaActualizar = ctx.bodyAsClass(Venta.class);
        ventaActualizar.setId(id);
        Venta ventaActualizada = ventaService.actualizarVenta(ventaActualizar);
        if (ventaActualizada == null) {
            throw new NotFoundResponse("Venta no encontrada");
        } else {
            ctx.status(200).json(new Mensaje("Venta actualizada correctamente", ventaActualizada));
        }
    }

    private void listarVentas(Context ctx) {
        ctx.status(200).json(new Mensaje("Lista de ventas", ventaService.obtenerTodasLasVentas()));
    }

}
