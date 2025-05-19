package org.apirest.modelo;

import java.time.LocalDate;
import java.util.List;

public class Carrito {
    private int id;
    private int idCliente;
    private List<ItemCarrito> items;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;

    public Carrito() {
    }

    public Carrito(int id, int idCliente, List<ItemCarrito> items, LocalDate fechaCreacion, LocalDate fechaModificacion) {
        this.id = id;
        this.idCliente = idCliente;
        this.items = items;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
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

    public List<ItemCarrito> getItems() {
        return items;
    }
    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }
    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
