package org.apirest.config;

import org.apirest.Util.Mensaje;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;

public class ExceptionsConfig {

    public static void registrarExcepciones(Javalin app){
        // MANEJO DE EXCEPCIONES CENTRALIZADO
        // Indica que el recurso específico solicitado por el cliente no pudo ser encontrado en el servidor.
        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ctx.status(404).json(new Mensaje(e.getMessage() != null ? e.getMessage() : "Recurso no encontrado", null));
        });

        // Indica que la solicitud del cliente no se pudo procesar debido a un error de sintaxis o formato.
        app.exception(BadRequestResponse.class, (e, ctx) -> {
            ctx.status(400).json(new Mensaje(e.getMessage() != null ? e.getMessage() : "Petición incorrecta", null));
        });

        // Excepción para manejar errores de JSON malformado no se puedo serializar o deserializar
        app.exception(JsonProcessingException.class, (e, ctx) -> { // Específico para Jackson
            ctx.status(400).json(new Mensaje("JSON inválido o malformado: " + e.getOriginalMessage(), null));
        });

        //Indica algun error al desarrollador o error de negocio
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400).json(new Mensaje("Argumento ilegal o datos de entrada inválidos: " + e.getMessage(), null));
        });

        // Manejador para IllegalStateException
        app.exception(IllegalStateException.class, (e, ctx) -> {
            ctx.status(409).json(new Mensaje("Conflicto o estado ilegal: " + e.getMessage(), null)); // 409 Conflict es apropiado aquí
        });
        
        // Manejador genérico para cualquier otra excepción no capturada antes
        app.exception(Exception.class, (e, ctx) -> {
            // Loguear el error
            System.err.println("Error no manejado capturado: " + e.getMessage());
            e.printStackTrace(); // Para desarrollo, considera un logger más robusto para producción
            ctx.status(500).json(new Mensaje("Error interno del servidor. Por favor, intente más tarde.", null));
        });
    }

}
