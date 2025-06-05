package org.apirest.repository;

import org.apirest.modelo.EstadoEnvio;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EstadoEnvioRepository {
    private final List<EstadoEnvio> estados = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public EstadoEnvio save(EstadoEnvio estado) {
        estado.setId(idCounter.getAndIncrement());
        estados.add(estado);
        return estado;
    }

    public Optional<EstadoEnvio> findById(int id) {
        return estados.stream().filter(estado -> estado.getId() == id).findFirst();
    }

    public List<EstadoEnvio> findAll() {
        return new ArrayList<>(estados);
    }

    public Optional<EstadoEnvio> findByNombre(String nombreEstado) {
        return estados.stream()
                .filter(estado -> estado.getNombreEstado().equalsIgnoreCase(nombreEstado))
                .findFirst();
    }

    public boolean deleteById(int id) {
        return estados.removeIf(estado -> estado.getId() == id);
    }

    public EstadoEnvio update(int id, EstadoEnvio estadoActualizado) {
        for (int i = 0; i < estados.size(); i++) {
            EstadoEnvio estado = estados.get(i);
            if (estado.getId() == id) {
                estados.set(i, estadoActualizado);
                return estadoActualizado;
            }
        }
        return null;
    }
}
