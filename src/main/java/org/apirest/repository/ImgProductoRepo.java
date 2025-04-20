package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.ImgProducto;

public class ImgProductoRepo {

    private final List<ImgProducto> imgsProductos = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    //Listar todos las imagenes de productos
    public List<ImgProducto> getIgms() {
        return imgsProductos;
    }

    //Listar una imagen de producto por id
    public ImgProducto getByIdImg(int id) {
        return imgsProductos.stream().filter(img -> img.getId() == id).findFirst().orElse(null);
    }

    //Listar imagenes de productos por id de producto
    public List<ImgProducto> getImgsByIdProducto(int idProducto) {
        return imgsProductos.stream().filter(img -> img.getIdProducto() == idProducto).toList();
    }

    //Agregar una imagen de producto
    public ImgProducto agrgarImg(ImgProducto img) {
        img.setId(id.getAndIncrement());
        imgsProductos.add(img);
        return img;
    }

    //Actualizar una imagen de producto
    public ImgProducto actualizarImg(ImgProducto imgNew){
        for (int i = 0; i < imgsProductos.size(); i++) {
            ImgProducto img = imgsProductos.get(i);
            if (img.getId() == imgNew.getId()) {
                imgsProductos.set(i, imgNew);
                return imgNew;
            }
        }
        return null;
    }

    //Eliminar una imagen de producto
    public boolean eliminarImg(int id) {
        return imgsProductos.removeIf(img -> img.getId() == id);
    }

    //Eliminar imagenes de productos por id de producto
    public boolean eliminarImgsByIdProducto(int idProducto) {
        return imgsProductos.removeIf(img -> img.getIdProducto() == idProducto);
    }
    

}
