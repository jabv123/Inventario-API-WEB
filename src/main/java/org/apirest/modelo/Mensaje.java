package org.apirest.modelo;

//CLASE DE UTILIDAD PARA ENVIAR MENSAJES EN JSON
public class Mensaje {
    private String mensaje;
    private Object data;

    public Mensaje(String mensaje, Object data) {
        this.mensaje = mensaje;
        this.data = data;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}