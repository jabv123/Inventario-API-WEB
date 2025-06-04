package org.apirest.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class EnvioSimulado {
    private String id;
    private String idVenta;
    private String direccionEnvio;
    private String estadoEnvio;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaActualizacionEstado;

    // Constructor vacío
    public EnvioSimulado() {}

    // Constructor completo
    public EnvioSimulado(String id, String idVenta, String direccionEnvio, 
                        String estadoEnvio, LocalDateTime fechaCreacion, 
                        LocalDateTime fechaActualizacionEstado) {
        this.id = id;
        this.idVenta = idVenta;
        this.direccionEnvio = direccionEnvio;
        this.estadoEnvio = estadoEnvio;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacionEstado = fechaActualizacionEstado;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacionEstado() {
        return fechaActualizacionEstado;
    }

    public void setFechaActualizacionEstado(LocalDateTime fechaActualizacionEstado) {
        this.fechaActualizacionEstado = fechaActualizacionEstado;
    }

    @Override
    public String toString() {
        return "EnvioSimulado{" +
                "id='" + id + '\'' +
                ", idVenta='" + idVenta + '\'' +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                ", estadoEnvio='" + estadoEnvio + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacionEstado=" + fechaActualizacionEstado +
                '}';
    }
}
