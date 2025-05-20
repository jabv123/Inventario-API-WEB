package org.apirest.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Factura {
    private String id;
    private String idVenta;
    private String numeroFactura;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaFactura;

    private double totalFacturado;
    private String datosClienteFactura;

    // Constructor vacío
    public Factura() {}

    // Constructor completo
    public Factura(String id, String idVenta, String numeroFactura,
                   LocalDateTime fechaFactura, double totalFacturado, String datosClienteFactura) {
        this.id = id;
        this.idVenta = idVenta;
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.totalFacturado = totalFacturado;
        this.datosClienteFactura = datosClienteFactura;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
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
