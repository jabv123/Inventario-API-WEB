package org.apirest.service;

import org.apirest.modelo.EstadoEnvio;
import org.apirest.repository.EstadoEnvioRepository;
import java.util.List;
import java.util.Optional;

public class EstadoEnvioService {
    private static EstadoEnvioService instance;
    private final EstadoEnvioRepository estadoRepository;

    private EstadoEnvioService() {
        this.estadoRepository = EstadoEnvioRepository.getInstance();
    }

    public static EstadoEnvioService getInstance() {
        if (instance == null) {
            instance = new EstadoEnvioService();
        }
        return instance;
    }

    public EstadoEnvio crearEstado(EstadoEnvio estado) {
        return estadoRepository.save(estado);
    }

    public Optional<EstadoEnvio> obtenerEstadoPorId(String id) {
        return estadoRepository.findById(id);
    }

    public List<EstadoEnvio> obtenerTodosLosEstados() {
        return estadoRepository.findAll();
    }

    public Optional<EstadoEnvio> obtenerEstadoPorNombre(String nombreEstado) {
        return estadoRepository.findByNombre(nombreEstado);
    }

    public Optional<EstadoEnvio> actualizarEstado(String id, EstadoEnvio estadoActualizado) {
        Optional<EstadoEnvio> estadoExistente = estadoRepository.findById(id);
        if (estadoExistente.isPresent()) {
            return Optional.of(estadoRepository.update(id, estadoActualizado));
        }
        return Optional.empty();
    }

    public boolean eliminarEstado(String id) {
        return estadoRepository.deleteById(id);
    }
}
