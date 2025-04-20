package org.apirest.modelo;

public class Persona {

    private String nombre;
    private String email;
    private String contraseaña;

    public Persona(){}


    public Persona(String nombre, String email, String contraseaña) {
        this.nombre = nombre;
        this.email = email;
        this.contraseaña = contraseaña;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getContraseaña() {
        return contraseaña;
    }

    public void setContraseaña(String contraseaña) {
        this.contraseaña = contraseaña;
    }

}

