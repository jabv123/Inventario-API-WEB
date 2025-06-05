package org.apirest.service;

import org.apirest.modelo.EstadoEnvio;
import org.apirest.repository.EstadoEnvioRepository;
import java.util.List;
import java.util.Optional;

public class EstadoEnvioService {
    private final EstadoEnvioRepository estadoRepository;

    public EstadoEnvioService(EstadoEnvioRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public EstadoEnvio crearEstado(EstadoEnvio estado) {
        return estadoRepository.save(estado);
    }

    public Optional<EstadoEnvio> obtenerEstadoPorId(int id) {
        return estadoRepository.findById(id);
    }

    public List<EstadoEnvio> obtenerTodosLosEstados() {
        return estadoRepository.findAll();
    }

    public Optional<EstadoEnvio> obtenerEstadoPorNombre(String nombreEstado) {
        return estadoRepository.findByNombre(nombreEstado);
    }

    public Optional<EstadoEnvio> actualizarEstado(int id, EstadoEnvio estadoActualizado) {
        Optional<EstadoEnvio> estadoExistente = estadoRepository.findById(id);
        if (estadoExistente.isPresent()) {
            return Optional.of(estadoRepository.update(id, estadoActualizado));
        }
        return Optional.empty();
    }

    public boolean eliminarEstado(int id) {
        return estadoRepository.deleteById(id);
    }

    public void crearEstadoEnvio(EstadoEnvio estadoEnvio) {

    }

    public Object obtenerTodosLosEstadosEnvio() {
            return null;
    }

    public EstadoEnvio actualizarEstadoEnvio(EstadoEnvio estadoEnvioActualizar) {
        return null;
    }

    public boolean eliminarEstadoEnvio(int id) {
        return false;
    }

    public EstadoEnvio obtenerEstadoEnvioPorId(int id) {
        return null;
    }
}
