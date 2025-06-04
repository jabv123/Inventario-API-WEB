package org.apirest.service;

import org.apirest.modelo.EnvioSimulado;
import org.apirest.modelo.EnvioSimulado;
import org.apirest.repository.EnvioSimuladoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EnvioSimuladoService {
    private static EnvioSimuladoService instance;
    private final EnvioSimuladoRepository envioRepository;

    private EnvioSimuladoService() {
        this.envioRepository = EnvioSimuladoRepository.getInstance();
    }

    public static EnvioSimuladoService getInstance() {
        if (instance == null) {
            instance = new EnvioSimuladoService();
        }
        return instance;
    }

    public EnvioSimulado crearEnvio(EnvioSimulado envio) {
        // Establecer fecha de creación actual si no se proporciona
        if (envio.getFechaCreacion() == null) {
            envio.setFechaCreacion(LocalDateTime.now());
        }
        
        // Establecer estado inicial si no se proporciona
        if (envio.getEstadoEnvio() == null || envio.getEstadoEnvio().isEmpty()) {
            envio.setEstadoEnvio("pendiente");
        }
        
        // Establecer fecha de actualización de estado
        envio.setFechaActualizacionEstado(LocalDateTime.now());
        
        return envioRepository.save(envio);
    }

    public Optional<EnvioSimulado> obtenerEnvioPorId(String id) {
        return envioRepository.findById(id);
    }

    public List<EnvioSimulado> obtenerTodosLosEnvios() {
        return envioRepository.findAll();
    }

    public Optional<EnvioSimulado> obtenerEnvioPorVenta(String idVenta) {
        return envioRepository.findByIdVenta(idVenta);
    }

    public Optional<EnvioSimulado> actualizarEstadoEnvio(String id, String nuevoEstado) {
        Optional<EnvioSimulado> envioExistente = envioRepository.findById(id);
        if (envioExistente.isPresent()) {
            EnvioSimulado envio = envioExistente.get();
            envio.setEstadoEnvio(nuevoEstado);
            envio.setFechaActualizacionEstado(LocalDateTime.now());
            return Optional.of(envioRepository.update(id, envio));
        }
        return Optional.empty();
    }

    public Optional<EnvioSimulado> actualizarEnvio(String id) {
        Optional<EnvioSimulado> envioExistente = envioRepository.findById(id);
        if (envioExistente.isPresent()) {
            EnvioSimulado envioActualizado = null;
            envioActualizado.setFechaActualizacionEstado(LocalDateTime.now());
            return Optional.of(envioRepository.update(id, envioActualizado));
        }
        return Optional.empty();
    }

    public boolean eliminarEnvio(String id) {
        return envioRepository.deleteById(id);
    }

    public Optional<EnvioSimulado> actualizarEnvio(String id, EnvioSimulado envioActualizado) {
        return null;
    }
}
