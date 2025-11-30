package org.apirest.repository;

import org.apirest.modelo.EnvioSimulado;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EnvioSimuladoRepository {
    private final List<EnvioSimulado> enviosList = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public EnvioSimulado save(EnvioSimulado envio) {
        envio.setId(idCounter.getAndIncrement());
        enviosList.add(envio);
        return envio;
    }

    public Optional<EnvioSimulado> findById(int id) {
        return enviosList.stream().filter(envio -> envio.getId() == id).findFirst();
    }

    public List<EnvioSimulado> findAll() {
        return new ArrayList<>(enviosList);
    }

    public Optional<EnvioSimulado> findByIdVenta(int idVenta) {
        return enviosList.stream()
                .filter(envio -> envio.getIdVenta() == idVenta)
                .findFirst();
    }

    public boolean deleteById(int id) {
        return enviosList.removeIf(envio -> envio.getId() == id);
    }

    public EnvioSimulado update(int id, EnvioSimulado envioActualizado) {
        for (int i = 0; i < enviosList.size(); i++) {
            if (enviosList.get(i).getId() == id) {
                envioActualizado.setId(id);
                enviosList.set(i, envioActualizado);
                return envioActualizado;
            }
        }
        return null;
    }

    public List<EnvioSimulado> findByIdEstadoEnvio(int idEstadoEnvio) {
        return enviosList.stream()
                .filter(envio -> envio.getIdEstadoEnvio() == idEstadoEnvio)
                .collect(Collectors.toList());
    }
}
