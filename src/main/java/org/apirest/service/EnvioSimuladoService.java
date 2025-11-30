package org.apirest.service;

import org.apirest.modelo.EnvioSimulado;
import org.apirest.modelo.EstadoEnvio;
import org.apirest.repository.EnvioSimuladoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EnvioSimuladoService {
    private final EnvioSimuladoRepository envioRepository;
    private final EstadoEnvioService estadoEnvioService;

    public EnvioSimuladoService(EnvioSimuladoRepository envioSimuladoRepository,
            EstadoEnvioService estadoEnvioService) {
        this.envioRepository = envioSimuladoRepository;
        this.estadoEnvioService = estadoEnvioService;
        // Inicializar estados predeterminados al crear el servicio
        this.estadoEnvioService.inicializarEstadosPredeterminados();
    }

    public EnvioSimulado crearEnvio(EnvioSimulado envio) {
        // Establecer fecha de creación actual si no se proporciona
        if (envio.getFechaCreacion() == null) {
            envio.setFechaCreacion(LocalDateTime.now());
        }

        // Establecer estado inicial si no se proporciona
        if (envio.getIdEstadoEnvio() == 0) {
            // Buscar el estado "Pendiente" o usar ID 1 por defecto
            Optional<EstadoEnvio> estadoPendiente = estadoEnvioService.obtenerEstadoPorNombre("Pendiente");
            if (estadoPendiente.isPresent()) {
                envio.setIdEstadoEnvio(estadoPendiente.get().getId());
            } else {
                envio.setIdEstadoEnvio(1); // Fallback al ID 1
            }
        }

        // Establecer fecha de actualización de estado
        envio.setFechaActualizacionEstado(LocalDateTime.now());

        return envioRepository.save(envio);
    }

    public Optional<EnvioSimulado> obtenerEnvioPorId(int id) {
        return envioRepository.findById(id);
    }

    public List<EnvioSimulado> obtenerTodosLosEnvios() {
        return envioRepository.findAll();
    }

    public Optional<EnvioSimulado> obtenerEnvioPorVenta(int idVenta) {
        return envioRepository.findByIdVenta(idVenta);
    }

    public Optional<EnvioSimulado> actualizarEstadoEnvio(int id, int nuevoIdEstado) {
        // Validar que el estado existe
        Optional<EstadoEnvio> estadoExiste = estadoEnvioService.obtenerEstadoPorId(nuevoIdEstado);
        if (!estadoExiste.isPresent()) {
            throw new IllegalArgumentException("El estado con ID " + nuevoIdEstado + " no existe");
        }

        Optional<EnvioSimulado> envioExistente = envioRepository.findById(id);
        if (!envioExistente.isPresent()) {
            return Optional.empty(); // Envío no encontrado - esto sí debe ser 404
        }

        EnvioSimulado envio = envioExistente.get();

        // Validar transición de estado
        if (!estadoEnvioService.esTransicionValida(envio.getIdEstadoEnvio(), nuevoIdEstado)) {
            String mensajeError = generarMensajeTransicionInvalida(envio.getIdEstadoEnvio(), nuevoIdEstado);
            throw new IllegalStateException(mensajeError);
        }

        envio.setIdEstadoEnvio(nuevoIdEstado);
        envio.setFechaActualizacionEstado(LocalDateTime.now());
        return Optional.of(envioRepository.update(id, envio));
    }

    public boolean eliminarEnvio(int id) {
        return envioRepository.deleteById(id);
    }

    public EnvioSimulado actualizarEnvio(int id, EnvioSimulado envioActualizado) {
        return envioRepository.update(id, envioActualizado);
    }

    /**
     * Obtiene todos los estados de envío disponibles
     */
    public List<EstadoEnvio> obtenerTodosLosEstados() {
        return estadoEnvioService.obtenerTodosLosEstados();
    }

    /**
     * Obtiene el estado actual de un envío específico
     */
    public Optional<EstadoEnvio> obtenerEstadoActual(int idEnvio) {
        Optional<EnvioSimulado> envio = envioRepository.findById(idEnvio);
        if (envio.isPresent()) {
            return estadoEnvioService.obtenerEstadoPorId(envio.get().getIdEstadoEnvio());
        }
        return Optional.empty();
    }

    /**
     * Avanza el envío al siguiente estado en el flujo normal
     */
    public Optional<EnvioSimulado> avanzarAlSiguienteEstado(int idEnvio) {
        Optional<EnvioSimulado> envio = envioRepository.findById(idEnvio);
        if (!envio.isPresent()) {
            return Optional.empty(); // Envío no encontrado - esto sí debe ser 404
        }

        int estadoActual = envio.get().getIdEstadoEnvio();

        // Verificar si es un estado final
        if (estadoActual == 4 || estadoActual == 5) { // Entregado o Cancelado
            String nombreEstado = obtenerNombreEstado(estadoActual);
            throw new IllegalStateException(
                    "El envío ya se encuentra en estado final '" + nombreEstado + "' y no puede avanzar más");
        }

        Optional<EstadoEnvio> siguienteEstado = estadoEnvioService.obtenerSiguienteEstado(estadoActual);
        if (siguienteEstado.isPresent()) {
            return actualizarEstadoEnvio(idEnvio, siguienteEstado.get().getId());
        }

        throw new IllegalStateException("No hay siguiente estado disponible para el estado actual");
    }

    /**
     * Obtiene envíos por estado usando el nombre del estado
     */
    public List<EnvioSimulado> obtenerEnviosPorNombreEstado(String nombreEstado) {
        Optional<EstadoEnvio> estado = estadoEnvioService.obtenerEstadoPorNombre(nombreEstado);
        if (estado.isPresent()) {
            return envioRepository.findByIdEstadoEnvio(estado.get().getId());
        }
        return List.of(); // Lista vacía si no se encuentra el estado
    }

    /**
     * Verifica si un envío puede cambiar a un estado específico
     */
    public boolean puedeTransicionarA(int idEnvio, int nuevoIdEstado) {
        Optional<EnvioSimulado> envio = envioRepository.findById(idEnvio);
        if (envio.isPresent()) {
            return estadoEnvioService.esTransicionValida(envio.get().getIdEstadoEnvio(), nuevoIdEstado);
        }
        return false;
    }

    /**
     * Genera un mensaje descriptivo para transiciones inválidas
     */
    private String generarMensajeTransicionInvalida(int estadoActual, int nuevoEstado) {
        String nombreEstadoActual = obtenerNombreEstado(estadoActual);
        String nombreNuevoEstado = obtenerNombreEstado(nuevoEstado);

        // Casos específicos
        if ((estadoActual == 4 || estadoActual == 5)) { // Entregado o Cancelado
            return "No se puede cambiar el estado de '" + nombreEstadoActual +
                    "' a '" + nombreNuevoEstado + "' porque el envío ya está en un estado final";
        }

        if (nuevoEstado < estadoActual && estadoActual <= 4) {
            return "No se puede retroceder del estado '" + nombreEstadoActual +
                    "' al estado '" + nombreNuevoEstado + "' porque no se permiten transiciones hacia atrás";
        }

        if (nuevoEstado > estadoActual + 1 && estadoActual < 4) {
            return "No se puede avanzar directamente del estado '" + nombreEstadoActual +
                    "' al estado '" + nombreNuevoEstado + "' porque debe seguir el flujo secuencial";
        }

        return "La transición del estado '" + nombreEstadoActual +
                "' al estado '" + nombreNuevoEstado + "' no está permitida";
    }

    /**
     * Obtiene el nombre del estado por su ID
     */
    private String obtenerNombreEstado(int idEstado) {
        Optional<EstadoEnvio> estado = estadoEnvioService.obtenerEstadoPorId(idEstado);
        return estado.isPresent() ? estado.get().getNombreEstado() : "Estado " + idEstado;
    }
}
