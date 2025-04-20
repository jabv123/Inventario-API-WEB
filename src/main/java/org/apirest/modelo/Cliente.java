package org.apirest.modelo;

public class Cliente extends Persona {

    int idCliente;
    String direccion;

    public Cliente() {
    }

    public Cliente(String nombre, String email, String contraseña, int idCliente, String direccion) {
        super(nombre, email, contraseña);
        this.direccion = direccion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
