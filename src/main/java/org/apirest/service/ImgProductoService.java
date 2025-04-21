package org.apirest.service;

import java.util.List;

import org.apirest.modelo.ImgProducto;
import org.apirest.repository.ImgProductoRepo;

public class ImgProductoService {

    private final ImgProductoRepo imgProductoRepo;

    public ImgProductoService(ImgProductoRepo imgProductoRepo){
        this.imgProductoRepo = imgProductoRepo;
    } 

    //Listar todas la imagenes
    public List<ImgProducto> getImgsProductos(){
        return imgProductoRepo.getIgms();
    }

    //Listar imgs por id
    public ImgProducto getImgById(int id){
        return imgProductoRepo.getByIdImg(id);
    }

    //Listar todas las imagenes por producto(id)
    public List<ImgProducto> getImgByProducto(int idProducto){
        return imgProductoRepo.getImgsByIdProducto(idProducto);
    }

    //Crear imgs para producto(se asigna el producto por el idProducto en peticion)
    public ImgProducto agregarImgProducto(ImgProducto img){
        return imgProductoRepo.agrgarImg(img);
    }

    //Actualizar img
    public ImgProducto actualizarImgProducto(ImgProducto img){
        return imgProductoRepo.actualizarImg(img);
    }

    //Eliminar img por id
    public boolean eliminarImg(int id){
        return imgProductoRepo.eliminarImg(id);
    }

    //Eliminar img por idProducto
    public boolean eliminarImgByProducto(int idProducto){
        return imgProductoRepo.eliminarImgsByIdProducto(idProducto);
    }




}
