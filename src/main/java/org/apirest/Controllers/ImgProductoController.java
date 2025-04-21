package org.apirest.Controllers;

import org.apirest.modelo.ImgProducto;
import org.apirest.modelo.Mensaje;
import org.apirest.service.ImgProductoService;

import com.fasterxml.jackson.databind.ObjectMapper;

import static spark.Spark.*;

public class ImgProductoController {

    private final ImgProductoService imgProductoService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ImgProductoController(ImgProductoService imgProductoService){
        this.imgProductoService = imgProductoService;
        rutasImgProducto();
    }

    private void rutasImgProducto(){

        path("/api/imgProductos", () -> {

            //Listar todas las img de productos
            get("", (req, res) -> {
                res.type("application/json");
                try{
                    //Validar si hay img de productos
                    if (imgProductoService.getImgsProductos().isEmpty()) {
                        res.status(404);
                        return "No hay imagenes de productos registrados";
                    } else {
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Imagenes de productos cargados", imgProductoService.getImgsProductos()));
                    }
                } catch (Exception e) {
                    return "error al obtener las imagenes de los productos: " + e.getMessage();
                }
            });

            //Listar img de productos por id de img
            get("/:id", (req, res) -> {
                res.type("application/json");
                try{
                    int id = Integer.parseInt(req.params(":id"));
                    //Validar si hay img de productos por id
                    if (imgProductoService.getImgById(id) == null) {
                        res.status(404);
                        return "No hay imagenes con el id: " + id;
                    } else {
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Imagen cargada", imgProductoService.getImgById(id)));
                    }
                } catch (Exception e) {
                    return "error al obtener la imagen de los productos: " + e.getMessage();
                }
            });

            //Listar img de productos por Producto
            get("/producto/:id", (req, res) -> {
                res.type("application/json");
                
                try{
                    int idProducto = Integer.parseInt(req.params(":id"));
                    //Validar si hay img de productos por idProducto
                    if (imgProductoService.getImgByProducto(idProducto).isEmpty()) {
                        res.status(404);
                        return "No hay imagenes para el producto con id: " + idProducto;
                    } else {
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Imagenes de producto" + idProducto + " cargadas", imgProductoService.getImgByProducto(idProducto)));
                    }
                } catch (Exception e) {
                    return "error al obtener las imagenes de los productos: " + e.getMessage();
                }
            });

            //Crear img de productos
            post("", (req, res) -> {
                res.type("application/json");
                try{
                    ImgProducto imgProducto = objectMapper.readValue(req.body(), ImgProducto.class);
                    res.status(201);
                    return objectMapper.writeValueAsString(new Mensaje("Imagen de producto creada", imgProductoService.agregarImgProducto(imgProducto)));
                } catch (Exception e) {
                    return "error al crear la imagen de los productos: " + e.getMessage();
                }
            });

            //Actualizar img de productos
            put("/:id", (req, res) -> {
                res.type("application/json");
                try{
                    int id = Integer.parseInt(req.params(":id"));
                    //Validar si hay img de productos por id
                    if (imgProductoService.getImgById(id) == null) {
                        res.status(404);
                        return "No hay imagenes de productos registradas";
                    }
                    res.status(200);
                    ImgProducto imgProducto = objectMapper.readValue(req.body(), ImgProducto.class);
                    imgProducto.setId(id);
                    return objectMapper.writeValueAsString(new Mensaje("Imagen de producto actualizada", imgProductoService.actualizarImgProducto(imgProducto)));
                } catch (Exception e) {
                    return "error al actualizar la imagen de los productos: " + e.getMessage();
                }
            });

            //Eliminar img de productos por id
            delete("/:id", (req, res) -> {
                res.type("application/json");
                try{
                    int id = Integer.parseInt(req.params(":id"));
                    //Validar si hay img de productos por id
                    if (imgProductoService.getImgById(id) == null) {
                        res.status(404);
                        return "No hay imagenes de productos registradas";
                    } else {
                        boolean eliminado = imgProductoService.eliminarImg(id);
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Imagen de producto eliminada", eliminado));
                    }
                } catch (Exception e) {
                    return "error al eliminar la imagen de los productos: " + e.getMessage();
                }
            
            });


            //Eliminar imgs de productos por idProducto
            delete("/producto/:id", (req, res) -> {
                res.type("application/json");
                try{
                    int idProducto = Integer.parseInt(req.params(":id"));
                    //Validar si hay img de productos por idProducto
                    if (imgProductoService.getImgByProducto(idProducto).isEmpty()) {
                        res.status(404);
                        return "No hay imagenes de productos registradas para el producto con id: " + idProducto;
                    } else {
                        boolean eliminadas = imgProductoService.eliminarImgByProducto(idProducto);
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Imagenes de producto eliminadas", eliminadas));
                    }
                } catch (Exception e) {
                    return "error al eliminar las imagenes de los productos: " + e.getMessage();
                }
            });

        });

    }
}