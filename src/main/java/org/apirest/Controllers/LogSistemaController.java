package org.apirest.Controllers;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;

import org.apirest.Util.Mensaje;
import org.apirest.modelo.LogSistema;
import org.apirest.service.LogSistemaService;

import io.javalin.http.Context;
public class LogSistemaController {

    private final LogSistemaService logSistemaService;

    public LogSistemaController(LogSistemaService logSistemaService) {
        this.logSistemaService = logSistemaService;
    }

    public void rutasLogSistema() {
        path("/api/logs-sistema", () -> {
            get(this::listarTodosLogs);

            path("/{idReferencia}", () -> {
                get(this::listarLogsPorIdReferencia);
            });
        });
    }

    private void listarTodosLogs(Context ctx) {
        List <LogSistema> logs = logSistemaService.listarTodosLogs();
        ctx.status(200).json(new Mensaje("Logs listados correctamente", logs));
    }

    private void listarLogsPorIdReferencia(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("idReferencia"));
        List<LogSistema> logs = logSistemaService.listarLogsIdReferencia(id);
        ctx.status(200).json(new Mensaje("Logs listados correctamente", logs));
    }
}
