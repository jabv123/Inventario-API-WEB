package org.apirest.modelo;

public class ImgProducto {

    private int id;
    private int idProducto;
    private String url;
    private int orden;

    public ImgProducto() {
    }

    public ImgProducto(int id, int idProducto, String url, int orden) {
        this.id = id;
        this.idProducto = idProducto;
        this.url = url;
        this.orden = orden;
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getOrden() {
        return orden;
    }
    public void setOrden(int orden) {
        this.orden = orden;
    }

}
