package org.apirest.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apirest.modelo.LogSistema;
import org.apirest.repository.LogSistemaRepo;

public class LogSistemaService {

    private final LogSistemaRepo logSistemaRepo;

    public LogSistemaService(LogSistemaRepo logSistemaRepo) {
        this.logSistemaRepo = logSistemaRepo;
    }

    public LogSistema registrarLog(String mensaje, int idReferencia) {
        LogSistema log = new LogSistema();
        log.setMensaje(mensaje);
        log.setIdReferencia(idReferencia);
        log.setFecha(LocalDateTime.now());
        return logSistemaRepo.add(log);
    }

    // Listar logs por ID de cliente
    public List<LogSistema> listarLogsIdReferencia(int idReferencia) {
        if (idReferencia <= 0) {
            throw new IllegalArgumentException("ID de referencia inválido");
        }
        return logSistemaRepo.findByUsuarioReferencia(idReferencia);
    }

    // Listar todos los logs
    public List<LogSistema> listarTodosLogs() {
        return logSistemaRepo.findAll();
    }
}
