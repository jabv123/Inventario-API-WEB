package org.apirest.modelo;

public class DetalleMetodoPago {

    private int id;
    private int idMetodoPago;
    private String clave;
    private String valor; // Valor del detalle, como el número de tarjeta (enmascarado), fecha de vencimiento, etc.
    //AQUI SE PODRIAN AÑADIR PROPIEDADES DE ESTADO PARA VISIBILIDAD, ENCRIPTACION, ETC.

    public DetalleMetodoPago() {
    }

    public DetalleMetodoPago(int idMetodoPago, String clave, String valor) {
        this.idMetodoPago = idMetodoPago;
        this.clave = clave;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdMetodoPago() {
        return idMetodoPago;
    }
    public void setIdMetodoPago(int idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }

}
