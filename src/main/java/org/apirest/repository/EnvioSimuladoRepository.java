package org.apirest.repository;

import org.apirest.modelo.EnvioSimulado;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EnvioSimuladoRepository {
    private static EnvioSimuladoRepository instance;
    private final Map<String, EnvioSimulado> envios;
    private final AtomicInteger idCounter;

    public EnvioSimuladoRepository() {
        this.envios = new ConcurrentHashMap<>();
        this.idCounter = new AtomicInteger(1);
    }

    public static EnvioSimuladoRepository getInstance() {
        if (instance == null) {
            instance = new EnvioSimuladoRepository();
        }
        return instance;
    }

    public EnvioSimulado save(EnvioSimulado envio) {
        if (envio.getId() == null || envio.getId().isEmpty()) {
            envio.setId(String.valueOf(idCounter.getAndIncrement()));
        }
        envios.put(envio.getId(), envio);
        return envio;
    }

    public Optional<EnvioSimulado> findById(String id) {
        return Optional.ofNullable(envios.get(id));
    }

    public List<EnvioSimulado> findAll() {
        return new ArrayList<>(envios.values());
    }

    public Optional<EnvioSimulado> findByIdVenta(String idVenta) {
        return envios.values().stream()
                .filter(envio -> envio.getIdVenta().equals(idVenta))
                .findFirst();
    }

    public boolean deleteById(String id) {
        return envios.remove(id) != null;
    }

    public EnvioSimulado update(String id, EnvioSimulado envioActualizado) {
        if (envios.containsKey(id)) {
            envioActualizado.setId(id);
            envios.put(id, envioActualizado);
            return envioActualizado;
        }
        return null;
    }

}
