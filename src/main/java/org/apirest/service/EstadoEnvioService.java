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

    /**
     * Inicializa estados predeterminados del sistema si no existen
     */
    public void inicializarEstadosPredeterminados() {
        // Verificar si ya existen estados
        List<EstadoEnvio> estadosExistentes = estadoRepository.findAll();
        if (estadosExistentes.isEmpty()) {
            // Crear estados básicos del flujo de envío
            crearEstado(new EstadoEnvio(1, "Pendiente"));
            crearEstado(new EstadoEnvio(2, "Procesando"));
            crearEstado(new EstadoEnvio(3, "En tránsito"));
            crearEstado(new EstadoEnvio(4, "Entregado"));
            crearEstado(new EstadoEnvio(5, "Cancelado"));
        }
    }

    /**
     * Verifica si un estado es válido para transición
     */
    public boolean esTransicionValida(int estadoActual, int nuevoEstado) {
        // Reglas básicas de transición de estados
        // Pendiente (1) -> Procesando (2) o Cancelado (5)
        // Procesando (2) -> En tránsito (3) o Cancelado (5)
        // En tránsito (3) -> Entregado (4)
        // Cancelado (5) y Entregado (4) son estados finales

        switch (estadoActual) {
            case 1: // Pendiente
                return nuevoEstado == 2 || nuevoEstado == 5; // Procesando o Cancelado
            case 2: // Procesando
                return nuevoEstado == 3 || nuevoEstado == 5; // En tránsito o Cancelado
            case 3: // En tránsito
                return nuevoEstado == 4; // Solo Entregado
            case 4: // Entregado
            case 5: // Cancelado
                return false; // Estados finales, no permiten transición
            default:
                return false;
        }
    }

    /**
     * Obtiene el siguiente estado recomendado en el flujo normal
     */
    public Optional<EstadoEnvio> obtenerSiguienteEstado(int estadoActual) {
        switch (estadoActual) {
            case 1: // Pendiente -> Procesando
                return obtenerEstadoPorId(2);
            case 2: // Procesando -> En tránsito
                return obtenerEstadoPorId(3);
            case 3: // En tránsito -> Entregado
                return obtenerEstadoPorId(4);
            default:
                return Optional.empty(); // No hay siguiente estado
        }
    }
}
