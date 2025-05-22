package org.apirest.Controllers;

import org.apirest.Util.Mensaje;
import org.apirest.modelo.Venta;
import org.apirest.service.VentaService;

import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.*;
//import io.javalin.http.BadRequestResponse;

public class VentaController {

    private final VentaService ventaService;
    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    public void rutasVentas(){
        path("/api/ventas", () -> {
            post(this::realizarVenta);
        });
    }

    private void realizarVenta(Context ctx) {
        Venta venta = ctx.bodyAsClass(Venta.class);
        ventaService.realizarVenta(venta);
        ctx.status(201).json(new Mensaje("Venta realizada correctamente", venta));
    }

}
