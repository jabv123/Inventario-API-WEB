package org.apirest.modelo;

import java.time.LocalDate;
import java.util.List;

public class Venta {    
    
    private int id;
    private LocalDate fechaVenta;
    private int idCliente;
    private int idMetodoPago;
    private String estado;
    private double total;
    private List<DetalleVenta> detalles;
    
    // Campos para manejo de cupones
    private String codigoCuponAplicado;
    private double descuentoAplicado;
    private double totalSinDescuento;

    public Venta() {
    }

    public Venta(int id, LocalDate fechaVenta, int idCliente, int idMetodoPago, String estado, double total, List<DetalleVenta> detalles) {
        this.id = id;
        this.fechaVenta = fechaVenta;
        this.idCliente = idCliente;
        this.idMetodoPago = idMetodoPago;
        this.estado = estado;
        this.total = total;
        this.detalles = detalles;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    // Getters y Setters para cupones
    public String getCodigoCuponAplicado() {
        return codigoCuponAplicado;
    }

    public void setCodigoCuponAplicado(String codigoCuponAplicado) {
        this.codigoCuponAplicado = codigoCuponAplicado;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public double getTotalSinDescuento() {
        return totalSinDescuento;
    }

    public void setTotalSinDescuento(double totalSinDescuento) {
        this.totalSinDescuento = totalSinDescuento;
    }
}
