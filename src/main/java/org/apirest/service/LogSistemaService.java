package org.apirest.service;

import java.util.List;

import org.apirest.modelo.LogSistema;
import org.apirest.repository.LogSistemaRepo;

public class LogSistemaService {

    private final LogSistemaRepo logSistemaRepo;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    public LogSistemaService(LogSistemaRepo logSistemaRepo, ClienteService clienteService, UsuarioService usuarioService) {
        this.logSistemaRepo = logSistemaRepo;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    public LogSistema registrarLogCliente(LogSistema log) {

        //Validar cliente
        if (log.getIdReferencia() <= 0 || clienteService.listarClientePorId(log.getIdReferencia()) == null) {
            throw new IllegalArgumentException("Cliente no encontrado o ID inválido");
        }

        return logSistemaRepo.add(log);
    }

    public LogSistema registrarLogUsuario(LogSistema log) {

        //Validar usuario
        if (log.getIdReferencia() <= 0 || usuarioService.getUsuario(log.getIdReferencia()) == null) {
            throw new IllegalArgumentException("Usuario no encontrado o ID inválido");
        }

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
