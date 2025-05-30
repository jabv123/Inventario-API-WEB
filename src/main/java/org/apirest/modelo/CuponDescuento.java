package org.apirest.modelo;

import java.time.LocalDate;

public class CuponDescuento {
    private int id;
    private String codigo;
    private double valor;
    private String tipoValor; // "porcentaje" o "fijo"
    private LocalDate fechaExpiracion;
    private boolean activo;

    // Constructor vacío
    public CuponDescuento() {
    }

    // Constructor completo
    public CuponDescuento(String codigo, double valor, String tipoValor, LocalDate fechaExpiracion, boolean activo) {
        this.codigo = codigo;
        this.valor = valor;
        this.tipoValor = tipoValor;
        this.fechaExpiracion = fechaExpiracion;
        this.activo = activo;
    }

    // Constructor sin ID (para creación)
    public CuponDescuento(int id, String codigo, double valor, String tipoValor, LocalDate fechaExpiracion, boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.valor = valor;
        this.tipoValor = tipoValor;
        this.fechaExpiracion = fechaExpiracion;
        this.activo = activo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "CuponDescuento{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", valor=" + valor +
                ", tipoValor='" + tipoValor + '\'' +
                ", fechaExpiracion=" + fechaExpiracion +
                ", activo=" + activo +
                '}';
    }
}
