package org.apirest.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse; // Importar NotFoundResponse
import org.apirest.modelo.EnvioSimulado;
import org.apirest.service.EnvioSimuladoService;
import org.apirest.Util.ResponseUtil; // Asumiendo que ResponseUtil es tu clase para envolver respuestas
import java.util.List;
import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.*; // Importar los métodos estáticos para path, get, post, etc.

public class EnvioSimuladoController {

    private final EnvioSimuladoService envioService;
    private final ObjectMapper objectMapper; // Aunque ObjectMapper se usa internamente, se mantiene aquí

    // 1. Modificar el constructor para inyectar el servicio
    public EnvioSimuladoController(EnvioSimuladoService envioService) {
        this.envioService = envioService; // Asignar el servicio inyectado
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();
    }

    // 2. Crear el método que define las rutas
    public void rutasEnvios() {
        path("/api/envios", () -> {
            post(this::crearEnvio);
            get(this::obtenerTodosLosEnvios);

            path("/{id}", () -> {
                get(this::obtenerEnvioPorId);
                put(this::actualizarEnvio);
                delete(this::eliminarEnvio);
            });

            // Rutas adicionales que tenías
            get("/venta/{idVenta}", this::obtenerEnvioPorVenta);
            put("/{id}/estado", this::actualizarEstadoEnvio); // Ojo: esta ruta usa un queryParam para 'estado'
        });
    }

    // Métodos de manejo de solicitudes (handlers)
    // No necesitan cambios sustanciales, solo se asegurarían de manejar sus propias excepciones.
    // He agregado el lanzamiento de NotFoundResponse explícitamente para seguir el patrón de VentaController.

    private void crearEnvio(Context ctx) {
        try {
            EnvioSimulado envio = ctx.bodyAsClass(EnvioSimulado.class); // Javalin puede mapear directamente
            EnvioSimulado envioCreado = envioService.crearEnvio(envio);
            ctx.status(201).json(ResponseUtil.success("Envío creado correctamente", envioCreado));
        } catch (Exception e) {
            ctx.status(400).json(ResponseUtil.error("Error al crear envío: " + e.getMessage()));
        }
    }

    private void obtenerTodosLosEnvios(Context ctx) {
        try {
            List<EnvioSimulado> envios = envioService.obtenerTodosLosEnvios();
            ctx.status(200).json(ResponseUtil.success("Envíos listados correctamente", envios));
        } catch (Exception e) {
            ctx.status(500).json(ResponseUtil.error("Error al obtener envíos: " + e.getMessage()));
        }
    }

    private void obtenerEnvioPorId(Context ctx) {
        String id = ctx.pathParam("id");
        Optional<EnvioSimulado> envio = envioService.obtenerEnvioPorId(id);
        if (envio.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Envío encontrado", envio.get()));
        } else {
            throw new NotFoundResponse("Envío no encontrado"); // Usar NotFoundResponse
        }
    }

    private void obtenerEnvioPorVenta(Context ctx) {
        String idVenta = ctx.pathParam("idVenta");
        Optional<EnvioSimulado> envio = envioService.obtenerEnvioPorVenta(idVenta);
        if (envio.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Envío de la venta encontrado", envio.get()));
        } else {
            throw new NotFoundResponse("No hay envío para la venta especificada"); // Usar NotFoundResponse
        }
    }

    private void actualizarEstadoEnvio(Context ctx) {
        String id = ctx.pathParam("id");
        String nuevoEstado = ctx.queryParam("estado");
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            ctx.status(400).json(ResponseUtil.error("El parámetro 'estado' es requerido"));
            return;
        }
        Optional<EnvioSimulado> envio = envioService.actualizarEstadoEnvio(id, nuevoEstado);
        if (envio.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Estado de envío actualizado correctamente", envio.get()));
        } else {
            throw new NotFoundResponse("Envío no encontrado"); // Usar NotFoundResponse
        }
    }

    private void actualizarEnvio(Context ctx) {
        String id = ctx.pathParam("id");
        // No necesitas objectMapper.readValue si usas ctx.bodyAsClass(EnvioSimulado.class)
        EnvioSimulado envioActualizado = ctx.bodyAsClass(EnvioSimulado.class);
        Optional<EnvioSimulado> envio = envioService.actualizarEnvio(id, envioActualizado);
        if (envio.isPresent()) {
            ctx.status(200).json(ResponseUtil.success("Envío actualizado correctamente", envio.get()));
        } else {
            throw new NotFoundResponse("Envío no encontrado"); // Usar NotFoundResponse
        }
    }

    private void eliminarEnvio(Context ctx) {
        String id = ctx.pathParam("id");
        boolean eliminado = envioService.eliminarEnvio(id);
        if (eliminado) {
            ctx.status(200).json(ResponseUtil.success("Envío eliminado correctamente", true));
        } else {
            throw new NotFoundResponse("Envío no encontrado"); // Usar NotFoundResponse
        }
    }
}