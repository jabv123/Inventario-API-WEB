package org.apirest.modelo;

import java.time.LocalDate;

public class AjusteStock {

    private int id;
    private int idProducto;
    private int cantidadAjuste;
    private String motivo;
    private LocalDate fechaAjuste;
    private int idUsuario;

    public AjusteStock() {
    }

    public AjusteStock(int idProducto, int cantidadAjuste, String motivo, LocalDate fechaAjuste, int idUsuario) {
        this.idProducto = idProducto;
        this.cantidadAjuste = cantidadAjuste;
        this.motivo = motivo;
        this.fechaAjuste = fechaAjuste;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidadAjuste() {
        return cantidadAjuste;
    }
    public void setCantidadAjuste(int cantidadAjuste) {
        this.cantidadAjuste = cantidadAjuste;
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getFechaAjuste() {
        return fechaAjuste;
    }
    public void setFechaAjuste(LocalDate fechaAjuste) {
        this.fechaAjuste = fechaAjuste;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
