package org.apirest.repository;

import org.apirest.modelo.EstadoEnvio;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EstadoEnvioRepository {
    private static EstadoEnvioRepository instance;
    private final Map<String, EstadoEnvio> estados;
    private final AtomicInteger idCounter;

    private EstadoEnvioRepository() {
        this.estados = new ConcurrentHashMap<>();
        this.idCounter = new AtomicInteger(1);
        initializeDefaultStates();
    }

    public static EstadoEnvioRepository getInstance() {
        if (instance == null) {
            instance = new EstadoEnvioRepository();
        }
        return instance;
    }

    private void initializeDefaultStates() {
        save(new EstadoEnvio("1", "pendiente"));
        save(new EstadoEnvio("2", "empaquetando"));
        save(new EstadoEnvio("3", "en_transito"));
        save(new EstadoEnvio("4", "entregado"));
    }

    public EstadoEnvio save(EstadoEnvio estado) {
        if (estado.getId() == null || estado.getId().isEmpty()) {
            estado.setId(String.valueOf(idCounter.getAndIncrement()));
        }
        estados.put(estado.getId(), estado);
        return estado;
    }

    public Optional<EstadoEnvio> findById(String id) {
        return Optional.ofNullable(estados.get(id));
    }

    public List<EstadoEnvio> findAll() {
        return new ArrayList<>(estados.values());
    }

    public Optional<EstadoEnvio> findByNombre(String nombreEstado) {
        return estados.values().stream()
                .filter(estado -> estado.getNombreEstado().equalsIgnoreCase(nombreEstado))
                .findFirst();
    }

    public boolean deleteById(String id) {
        return estados.remove(id) != null;
    }

    public EstadoEnvio update(String id, EstadoEnvio estadoActualizado) {
        if (estados.containsKey(id)) {
            estadoActualizado.setId(id);
            estados.put(id, estadoActualizado);
            return estadoActualizado;
        }
        return null;
    }
}
