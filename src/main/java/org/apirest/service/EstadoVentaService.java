package org.apirest.service;

import org.apirest.modelo.EstadoVenta;
import org.apirest.repository.EstadoVentaRepository;

import java.util.List;
import java.util.Optional;

public class EstadoVentaService {
    private static EstadoVentaService instance;
    private final EstadoVentaRepository estadoRepository;

    private EstadoVentaService() {
        this.estadoRepository = EstadoVentaRepository.getInstance();
    }

    public static EstadoVentaService getInstance() {
        if (instance == null) {
            instance = new EstadoVentaService();
        }
        return instance;
    }

    public EstadoVenta crearEstado(EstadoVenta estado) {
        return estadoRepository.save(estado);
    }

    public Optional<EstadoVenta> obtenerEstadoPorId(String id) {
        return estadoRepository.findById(id);
    }

    public List<EstadoVenta> obtenerTodosLosEstados() {
        return estadoRepository.findAll();
    }

    public Optional<EstadoVenta> obtenerEstadoPorNombre(String nombreEstado) {
        return estadoRepository.findByNombre(nombreEstado);
    }

    public Optional<EstadoVenta> actualizarEstado(String id, EstadoVenta estadoActualizado) {
        Optional<EstadoVenta> estadoExistente = estadoRepository.findById(id);
        if (estadoExistente.isPresent()) {
            return Optional.of(estadoRepository.update(id, estadoActualizado));
        }
        return Optional.empty();
    }

    public boolean eliminarEstado(String id) {
        return estadoRepository.deleteById(id);
    }

    public void crearEstadoVenta(EstadoVenta estadoVenta) {

    }

}
