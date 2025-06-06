package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import org.apirest.modelo.Factura;
import org.apirest.service.FacturaService;
import org.apirest.Util.ResponseUtil;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;
import java.util.Optional;

public class FacturaController {
    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    public void rutasFacturas() {
        path("/api/facturas", () -> {
            post(this::crearFactura);
            get(this::obtenerTodasLasFacturas);
            path("/{id}", () -> {
                get(this::obtenerFacturaPorId);
                put(this::actualizarFactura);
                delete(this::eliminarFactura);
            });
            path("/venta/{idVenta}", () -> {
                get(this::obtenerFacturasPorVenta);
            });
        });
    }


    private void crearFactura(Context ctx) {
            Factura factura = ctx.bodyAsClass(Factura.class);
            Factura facturaCreada = facturaService.crearFactura(factura);
            ctx.status(201).json(ResponseUtil.success("Factura creada correctamente", facturaCreada));
    }

    private void obtenerTodasLasFacturas(Context ctx) {
        List<Factura> facturas = facturaService.obtenerTodasLasFacturas();
        if (facturas.isEmpty()) {
            ctx.status(404).json(ResponseUtil.error("No hay facturas registradas"));
        } else {
            ctx.status(200).json(ResponseUtil.success("Facturas listadas correctamente", facturas));
        }
    }

    private void obtenerFacturaPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<Factura> factura = facturaService.obtenerFacturaPorId(id);
        if (factura.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Factura encontrada", factura.get()));
        } else {
            throw new NotFoundResponse("Factura no encontrada");
        }
    }

    public void obtenerFacturasPorVenta(Context ctx) {
        int idVenta = Integer.parseInt(ctx.pathParam("idVenta"));
        List<Factura> facturas = facturaService.obtenerFacturasPorVenta(idVenta);
        if (facturas.isEmpty()) {
            throw new NotFoundResponse("No se encontraron facturas para la venta con ID: " + idVenta);
        } else {
            ctx.status(200).json(ResponseUtil.success("Facturas de la venta encontradas", facturas));
        }
    }

    public void actualizarFactura(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Factura facturaActualizada = ctx.bodyAsClass(Factura.class);
        Optional<Factura> factura = facturaService.actualizarFactura(id, facturaActualizada);
        if (factura.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Factura actualizada correctamente", factura.get()));
        } else {
            throw new NotFoundResponse("Factura no encontrada para actualizar");
        }
    }

    public void eliminarFactura(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean eliminada = facturaService.eliminarFactura(id);
        if (eliminada) {
            ctx.status(200).json(ResponseUtil.success("Factura eliminada correctamente", true));
        } else {
            throw new NotFoundResponse("Factura no encontrada para eliminar");
        }
    }
}
