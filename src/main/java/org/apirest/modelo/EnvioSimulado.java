package org.apirest.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class EnvioSimulado {
    private int id;
    private int idVenta;
    private String direccionEnvio;
    private int idEstadoEnvio;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaActualizacionEstado;

    // Constructor vacío
    public EnvioSimulado() {
    } // Constructor completo

    public EnvioSimulado(int id, int idVenta, String direccionEnvio,
            int idEstadoEnvio, LocalDateTime fechaCreacion,
            LocalDateTime fechaActualizacionEstado) {
        this.id = id;
        this.idVenta = idVenta;
        this.direccionEnvio = direccionEnvio;
        this.idEstadoEnvio = idEstadoEnvio;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacionEstado = fechaActualizacionEstado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public int getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(int idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
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
                ", idEstadoEnvio='" + idEstadoEnvio + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacionEstado=" + fechaActualizacionEstado +
                '}';
    }
}
