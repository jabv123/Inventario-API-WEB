package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.apirest.modelo.EnvioSimulado;
import org.apirest.modelo.EstadoEnvio;
import org.apirest.service.EnvioSimuladoService;
import org.apirest.Util.ResponseUtil;
import java.util.List;
import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EnvioSimuladoController {

    private final EnvioSimuladoService envioService;

    // constructor para inyectar el servicio
    public EnvioSimuladoController(EnvioSimuladoService envioService) {
        this.envioService = envioService; // Asignar el servicio inyectado
    }

    public void rutasEnvios() {
        path("/api/envios", () -> {
            // Rutas básicas para envíos
            post(this::crearEnvio);
            get(this::obtenerTodosLosEnvios);
            get("/estados", this::obtenerTodosLosEstados);
            get("/por-estado/{nombreEstado}", this::obtenerEnviosPorNombreEstado);
            path("{id}", () -> {
                // Rutas para un envío específico
                get(this::obtenerEnvioPorId);
                put("/estado", this::actualizarEstadoEnvio);
                put("/avanzar-estado", this::avanzarAlSiguienteEstado);
                get("/estado-actual", this::obtenerEstadoActual);
                get("/puede-cambiar-a/{idEstado}", this::verificarTransicion);
            });

            get("/venta/{idVenta}", this::obtenerEnvioPorVenta);
            // Rutas para gestión de estados (simplificadas)

        });
    }

    private void crearEnvio(Context ctx) {
        EnvioSimulado envio = ctx.bodyAsClass(EnvioSimulado.class);
        EnvioSimulado envioCreado = envioService.crearEnvio(envio);
        ctx.status(201).json(ResponseUtil.success("Envío creado correctamente", envioCreado));
    }

    private void obtenerTodosLosEnvios(Context ctx) {
        List<EnvioSimulado> envios = envioService.obtenerTodosLosEnvios();
        ctx.status(200).json(ResponseUtil.success("Envíos listados correctamente", envios));
    }

    private void obtenerEnvioPorId(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<EnvioSimulado> envio = envioService.obtenerEnvioPorId(id);
        if (envio.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Envío encontrado", envio.get()));
        } else {
            throw new NotFoundResponse("Envío no encontrado"); // Usar NotFoundResponse
        }
    }

    private void obtenerEnvioPorVenta(Context ctx) {
        int idVenta = Integer.parseInt(ctx.pathParam("idVenta"));
        Optional<EnvioSimulado> envio = envioService.obtenerEnvioPorVenta(idVenta);
        if (envio.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Envío de la venta encontrado", envio.get()));
        } else {
            throw new NotFoundResponse("No hay envío para la venta especificada"); // Usar NotFoundResponse
        }
    }

    private void actualizarEstadoEnvio(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String estadoParam = ctx.queryParam("estado");
        if (estadoParam == null || estadoParam.isEmpty()) {
            throw new IllegalArgumentException("El parámetro 'estado' (ID del estado) es requerido");
        }

        int nuevoIdEstado = Integer.parseInt(estadoParam);
        Optional<EnvioSimulado> envio = envioService.actualizarEstadoEnvio(id, nuevoIdEstado);
        if (envio.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Estado de envío actualizado correctamente", envio.get()));
        } else {
            throw new NotFoundResponse("Envío no encontrado");
        }
    }// Métodos simplificados para gestión de estados

    private void obtenerTodosLosEstados(Context ctx) {
        List<EstadoEnvio> estados = envioService.obtenerTodosLosEstados();
        ctx.status(200).json(ResponseUtil.success("Estados de envío disponibles", estados));
    }

    private void obtenerEstadoActual(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<EstadoEnvio> estado = envioService.obtenerEstadoActual(id);
        if (estado.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Estado actual del envío", estado.get()));
        } else {
            throw new NotFoundResponse("Envío no encontrado");
        }
    }

    private void avanzarAlSiguienteEstado(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Optional<EnvioSimulado> envio = envioService.avanzarAlSiguienteEstado(id);
        if (envio.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Envío avanzado al siguiente estado", envio.get()));
        } else {
            throw new NotFoundResponse("Envío no encontrado");
        }
    }

    private void obtenerEnviosPorNombreEstado(Context ctx) {
        String nombreEstado = ctx.pathParam("nombreEstado");
        List<EnvioSimulado> envios = envioService.obtenerEnviosPorNombreEstado(nombreEstado);
        ctx.status(200).json(ResponseUtil.success("Envíos encontrados por estado", envios));
    }

    private void verificarTransicion(Context ctx) {
        int idEnvio = Integer.parseInt(ctx.pathParam("id"));
        int idEstado = Integer.parseInt(ctx.pathParam("idEstado"));
        boolean puedeTransicionar = envioService.puedeTransicionarA(idEnvio, idEstado);

        String mensaje = puedeTransicionar ? "La transición es válida" : "La transición no es válida";
        ctx.status(200).json(ResponseUtil.success(mensaje, puedeTransicionar));
    }
}