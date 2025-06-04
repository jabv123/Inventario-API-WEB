package org.apirest.modelo;

public class EstadoVenta {
    private String id;
    private String nombreEstado;

    // Constructor vacío
    public EstadoVenta() {}

    // Constructor completo
    public EstadoVenta(String id, String nombreEstado) {
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
        return "EstadoVenta{" +
                "id='" + id + '\'' +
                ", nombreEstado='" + nombreEstado + '\'' +
                '}';
    }
}
