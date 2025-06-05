package org.apirest.modelo;

public class EstadoEnvio {
    private String id;
    private String nombreEstado;

    // Constructor vacío
    public EstadoEnvio() {}

    // Constructor completo
    public EstadoEnvio(String id, String nombreEstado) {
        this.id = id;
        this.nombreEstado = nombreEstado;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    @Override
    public String toString() {
        return "EstadoEnvio{" +
                "id='" + id + '\'' +
                ", nombreEstado='" + nombreEstado + '\'' +
                '}';
    }
}
