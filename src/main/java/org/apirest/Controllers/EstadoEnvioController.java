package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.apirest.Util.Mensaje; // O ResponseUtil si lo prefieres usar consistentemente
import org.apirest.modelo.EstadoEnvio; // Asume que tienes esta clase de modelo
import org.apirest.service.EstadoEnvioService; // Asume que tienes esta clase de servicio

import static io.javalin.apibuilder.ApiBuilder.*;

public class EstadoEnvioController {

    private final EstadoEnvioService estadoEnvioService;

    public EstadoEnvioController(EstadoEnvioService estadoEnvioService) {
        this.estadoEnvioService = estadoEnvioService;
    }

    public void rutasEstadoEnvio() {
        path("/api/estado-envios", () -> {
            post(this::crearEstadoEnvio);
            get(this::listarEstadosEnvio);
            path("/{id}", () -> {
                get(this::obtenerEstadoEnvioPorId);
                put(this::actualizarEstadoEnvio);
                delete(this::eliminarEstadoEnvio);
            });
        });
    }

    private void crearEstadoEnvio(Context ctx) {
        try {
            EstadoEnvio estadoEnvio = ctx.bodyAsClass(EstadoEnvio.class);
            estadoEnvioService.crearEstadoEnvio(estadoEnvio);
            ctx.status(201).json(new Mensaje("Estado de envío creado correctamente", estadoEnvio));
        } catch (Exception e) {
            ctx.status(400).json(new Mensaje("Error al crear estado de envío: " + e.getMessage(), null));
        }
    }

    private void listarEstadosEnvio(Context ctx) {
        try {
            ctx.status(200).json(new Mensaje("Lista de estados de envío", estadoEnvioService.obtenerTodosLosEstadosEnvio()));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al listar estados de envío: " + e.getMessage(), null));
        }
    }

    private void obtenerEstadoEnvioPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        EstadoEnvio estadoEnvio = estadoEnvioService.obtenerEstadoEnvioPorId(id);
        if (estadoEnvio == null) {
            throw new NotFoundResponse("Estado de envío no encontrado");
        } else {
            ctx.status(200).json(new Mensaje("Estado de envío encontrado", estadoEnvio));
        }
    }

    private void actualizarEstadoEnvio(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        EstadoEnvio estadoEnvioActualizar = ctx.bodyAsClass(EstadoEnvio.class);
        estadoEnvioActualizar.setId(id); // Asegúrate de que tu modelo EstadoEnvio tenga un método setId
        EstadoEnvio estadoEnvioActualizado = estadoEnvioService.actualizarEstadoEnvio(estadoEnvioActualizar);
        if (estadoEnvioActualizado == null) {
            throw new NotFoundResponse("Estado de envío no encontrado");
        } else {
            ctx.status(200).json(new Mensaje("Estado de envío actualizado correctamente", estadoEnvioActualizado));
        }
    }

    private void eliminarEstadoEnvio(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean eliminado = estadoEnvioService.eliminarEstadoEnvio(id);
        if (!eliminado) {
            throw new NotFoundResponse("Estado de envío no encontrado");
        } else {
            ctx.status(204).json(new Mensaje("Estado de envío eliminado correctamente", null)); // 204 No Content
        }
    }
}