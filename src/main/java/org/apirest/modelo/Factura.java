package org.apirest.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Factura {
    private int id;
    private int idVenta;
    private String numeroFactura;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaFactura;

    private double totalFacturado;
    private String datosClienteFactura;

    public Factura() {}

    public Factura(int id, int idVenta, String numeroFactura,
                   LocalDateTime fechaFactura, double totalFacturado, String datosClienteFactura) {
        this.id = id;
        this.idVenta = idVenta;
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.totalFacturado = totalFacturado;
        this.datosClienteFactura = datosClienteFactura;
    }

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

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDateTime getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(LocalDateTime fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public double getTotalFacturado() {
        return totalFacturado;
    }

    public void setTotalFacturado(double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }

    public String getDatosClienteFactura() {
        return datosClienteFactura;
    }

    public void setDatosClienteFactura(String datosClienteFactura) {
        this.datosClienteFactura = datosClienteFactura;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id='" + id + '\'' +
                ", idVenta='" + idVenta + '\'' +
                ", numeroFactura='" + numeroFactura + '\'' +
                ", fechaFactura=" + fechaFactura +
                ", totalFacturado=" + totalFacturado +
                ", datosClienteFactura='" + datosClienteFactura + '\'' +
                '}';
    }
}
