package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.LogSistema;

public class LogSistemaRepo {

    private final List<LogSistema> logs = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    public LogSistema add(LogSistema log) {
        log.setId(id.getAndIncrement());
        logs.add(log);
        return log;
    }

    public List<LogSistema> findByUsuarioReferencia(int idReferencia) {
        return logs.stream()
                .filter(log -> log.getIdReferencia() == idReferencia)
                .toList();
    }

    public List<LogSistema> findAll() {
        return logs;
    }
}
