package org.apirest.modelo;

import java.time.LocalDate;
import java.util.List;

public class MetodoPago {
    private int id;
    private int idCliente;
    private String tipoPago; // Ejemplo: "Tarjeta de Crédito", "PayPal", etc.
    private List<DetalleMetodoPago> detalles; // Detalles del método de pago, como el número de tarjeta (enmascarado), fecha de vencimiento, etc.
    private boolean activo; // Indica si el método de pago está activo o no
    private LocalDate fechaCreacion; // Fecha de creación del método de pago

    public MetodoPago() {
    }

    public MetodoPago(int idCliente, String tipoPago, List<DetalleMetodoPago> detalles, boolean activo, LocalDate fechaCreacion) {
        this.idCliente = idCliente;
        this.tipoPago = tipoPago;
        this.detalles = detalles;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoPago() {
        return tipoPago;
    }
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public List<DetalleMetodoPago> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleMetodoPago> detalles) {
        this.detalles = detalles;
    }

    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
