package org.apirest.modelo;

import java.time.LocalDateTime;

public class LogSistema {

    private int id;
    private LocalDateTime fecha;
    private String mensaje;
    private int idReferencia;

    public LogSistema(){}

    public LogSistema(LocalDateTime fecha, String mensaje, int idReferencia) {
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.idReferencia = idReferencia;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getIdReferencia() {
        return idReferencia;
    }
    public void setIdReferencia(int idReferencia) {
        this.idReferencia = idReferencia;
    }
}
