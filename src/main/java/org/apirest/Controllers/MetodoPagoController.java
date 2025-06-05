package org.apirest.Controllers;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import java.util.List;

import org.apirest.Util.Mensaje;
import org.apirest.modelo.DetalleMetodoPago;
import org.apirest.modelo.MetodoPago;
import org.apirest.service.MetodoPagoService;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    public MetodoPagoController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    public void rutasMetodoPago(){

        path("/api/metodos-pago", () -> {
            post(this::crearMetodoPago);
            get(this::obtenerTodosMetodosPago);
            path("/cliente/{idCliente}", () -> {
                get(this::obtenerMetodosPagoPorCliente);
            });
            path("{id}", () -> {
                put(this::actualizarMetodoPago);
                patch(this::actualizarEstadoMetodoPago);
            });
            path("/{id}/detalles", () -> {
                get(this::obtenerDetallesMetodoPago);
            });
        });
    }

    private void crearMetodoPago(Context ctx) {
        MetodoPago metodoPago = ctx.bodyAsClass(MetodoPago.class);
        metodoPagoService.crearMetodoPago(metodoPago);
        ctx.status(201).json(new Mensaje("Método de pago creado exitosamente", metodoPago));
    }

    private void obtenerTodosMetodosPago(Context ctx) {
        var metodosPago = metodoPagoService.obtenerTodosMetodoPago();
        ctx.status(200).json(new Mensaje("Métodos de pago obtenidos exitosamente", metodosPago));
    }

    private void actualizarMetodoPago(Context ctx) {
        int idMetodoPago = Integer.parseInt(ctx.pathParam("id"));
        MetodoPago metodoPago = ctx.bodyAsClass(MetodoPago.class);
        metodoPago.setId(idMetodoPago);
        MetodoPago metodoPagoActualizado = metodoPagoService.actualizarMetodoPago(metodoPago);
        ctx.status(200).json(new Mensaje("Método de pago y detalles actualizados exitosamente", metodoPagoActualizado));
    }

    private void actualizarEstadoMetodoPago(Context ctx) {
        int idMetodoPago = Integer.parseInt(ctx.pathParam("id"));
        boolean estado = Boolean.parseBoolean(ctx.queryParam("estado"));
        boolean metodoPagoActualizado = metodoPagoService.actualizarEstadoMetodoPago(idMetodoPago, estado);
        ctx.status(200).json(new Mensaje("Estado del método de pago actualizado exitosamente", metodoPagoActualizado));
    }

    private void obtenerMetodosPagoPorCliente(Context ctx) {
        int idCliente = Integer.parseInt(ctx.pathParam("idCliente"));
        List<MetodoPago> metodoPago = metodoPagoService.obtenerMetodosCliente(idCliente);
        if (metodoPago.isEmpty()) {
            throw new NotFoundResponse("No se encontraron métodos de pago para el cliente con ID: " + idCliente);
        }
        ctx.status(200).json(new Mensaje("Métodos de pago obtenidos exitosamente", metodoPago));
    }

    private void obtenerDetallesMetodoPago(Context ctx) {
        int idMetodoPago = Integer.parseInt(ctx.pathParam("id"));
        List<DetalleMetodoPago> detalles = metodoPagoService.obtenerDetalleMetodoPago(idMetodoPago);
        if (detalles == null || detalles.isEmpty()) {
            throw new NotFoundResponse("No se encontraron detalles para el método de pago con ID: " + idMetodoPago);
        }
        ctx.status(200).json(new Mensaje("Detalles del método de pago obtenidos exitosamente", detalles));
    }
}
