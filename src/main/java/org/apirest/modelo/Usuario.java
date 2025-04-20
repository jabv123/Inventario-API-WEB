package org.apirest.modelo;

public class Usuario  extends Persona {

    private int idUsuario;
    private String rol;


    public Usuario() {
    }

    public Usuario(String nombre, String email, String contraseña, int idUsuario, String rol) {
        super(nombre, email, contraseña);
        this.idUsuario = idUsuario;
        this.rol = rol;
    }
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
