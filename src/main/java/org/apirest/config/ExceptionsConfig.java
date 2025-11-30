package org.apirest.config;

import org.apirest.Util.ResponseUtil;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;

public class ExceptionsConfig {

    public static void registrarExcepciones(Javalin app) { // MANEJO DE EXCEPCIONES CENTRALIZADO
        // Indica que el recurso específico solicitado por el cliente no pudo ser
        // encontrado en el servidor.
        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ctx.status(404).json(ResponseUtil.error(e.getMessage() != null ? e.getMessage() : "Recurso no encontrado"));
        });

        // Indica que la solicitud del cliente no se pudo procesar debido a un error de sintaxis o formato.
        app.exception(BadRequestResponse.class, (e, ctx) -> {
            ctx.status(400).json(ResponseUtil.error(e.getMessage() != null ? e.getMessage() : "Petición incorrecta"));
        });

        // Excepción para manejar errores de JSON malformado no se puedo serializar o deserializar
        app.exception(JsonProcessingException.class, (e, ctx) -> {
            ctx.status(400).json(ResponseUtil.error("JSON inválido o malformado: " + e.getOriginalMessage()));
        });

        // Indica algun error al desarrollador o error de negocio
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400).json(ResponseUtil.error("Argumento ilegal o datos de entrada inválidos: " + e.getMessage()));
        });

        // Manejador para NumberFormatException (IDs inválidos, formatos numéricos
        // incorrectos)
        app.exception(NumberFormatException.class, (e, ctx) -> {
            ctx.status(400).json(ResponseUtil.error("Formato de número inválido: " + e.getMessage()));
        });

        // Manejador para IllegalStateException
        app.exception(IllegalStateException.class, (e, ctx) -> {
            ctx.status(409).json(ResponseUtil.error("Conflicto o estado ilegal: " + e.getMessage()));
        });

        // Manejador genérico para cualquier otra excepción no capturada antes
        app.exception(Exception.class, (e, ctx) -> {
            // Loguear el error
            System.err.println("Error no manejado capturado: " + e.getMessage());
            e.printStackTrace();
            ctx.status(500).json(ResponseUtil.error("Error interno del servidor. Por favor, intente más tarde."));
        });
    }

}
