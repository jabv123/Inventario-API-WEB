package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse; // Añadida importación

import org.apirest.modelo.ImgProducto;
import org.apirest.modelo.Mensaje;
import org.apirest.service.ImgProductoService;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ImgProductoController {

    private final ImgProductoService imgProductoService;

    public ImgProductoController(ImgProductoService imgProductoService) {
        this.imgProductoService = imgProductoService;
    }

    public void addImgProductoRoutes() {
        path("/api/imgProductos", () -> {
            get(this::getAll);
            post(this::create);

            path("producto/{id}", () -> {
                get(this::getByProducto);
                delete(this::deleteByProducto);
            });

            path("{id}", () -> {
                get(this::getById);
                put(this::update);
                delete(this::deleteById);
            });
        });
    }

    private void getAll(Context ctx) {
        var list = imgProductoService.getImgsProductos();
        if (list.isEmpty()) {
            ctx.status(404).json(new Mensaje("No hay imágenes de productos registradas", null));
        } else {
            ctx.status(200).json(new Mensaje("Imágenes de productos cargadas", list));
        }
    }

    private void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var img = imgProductoService.getImgById(id);
            if (img == null) {
                ctx.status(404).json(new Mensaje("No hay imagen con el id: " + id, null));
            } else {
                ctx.status(200).json(new Mensaje("Imagen cargada", img));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            // Considerar loggear la excepción e.g., LOGGER.error("Error al obtener la imagen", e);
            ctx.status(500).json(new Mensaje("Error al obtener la imagen: " + e.getMessage(), null));
        }
    }

    private void getByProducto(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            var imgs = imgProductoService.getImgByProducto(id);
            if (imgs.isEmpty()) {
                ctx.status(404).json(new Mensaje("No hay imágenes para el producto con id: " + id, null));
            } else {
                ctx.status(200).json(new Mensaje("Imágenes de producto " + id + " cargadas", imgs));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID de producto inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            // Considerar loggear la excepción
            ctx.status(500).json(new Mensaje("Error al obtener las imágenes por producto: " + e.getMessage(), null));
        }
    }

    private void create(Context ctx) {
        try {
            ImgProducto imgProducto = ctx.bodyAsClass(ImgProducto.class); // Cambiado
            // Aquí se podría añadir validación para imgProducto si es necesario
            var creado = imgProductoService.agregarImgProducto(imgProducto);
            ctx.status(201).json(new Mensaje("Imagen de producto creada", creado));
        } catch (BadRequestResponse e) { // Cambiado para atrapar BadRequestResponse
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            // Considerar loggear la excepción
            ctx.status(500).json(new Mensaje("Error al crear la imagen: " + e.getMessage(), null));
        }
    }

    private void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ImgProducto imgProducto = ctx.bodyAsClass(ImgProducto.class); // Cambiado
            
            if (imgProductoService.getImgById(id) == null) {
                ctx.status(404).json(new Mensaje("No hay imagen con el ID: " + id + " para actualizar", null));
                return;
            }
            imgProducto.setId(id); // Asegurar que el ID en el cuerpo coincida o se use el de la URL
            var actualizado = imgProductoService.actualizarImgProducto(imgProducto);
            ctx.status(200).json(new Mensaje("Imagen de producto actualizada", actualizado));
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (BadRequestResponse e) { // Cambiado para atrapar BadRequestResponse
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            // Considerar loggear la excepción
            ctx.status(500).json(new Mensaje("Error al actualizar la imagen: " + e.getMessage(), null));
        }
    }

    private void deleteById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if (imgProductoService.getImgById(id) == null) {
                ctx.status(404).json(new Mensaje("No hay imagen con el ID: " + id + " para eliminar", null));
                return;
            }
            boolean eliminado = imgProductoService.eliminarImg(id);
            if (eliminado) {
                ctx.status(200).json(new Mensaje("Imagen eliminada correctamente", true));
            } else {
                // Esto podría indicar un error lógico si getImgById no fue null pero no se pudo eliminar
                ctx.status(500).json(new Mensaje("Error al eliminar la imagen con ID: " + id, false));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            // Considerar loggear la excepción
            ctx.status(500).json(new Mensaje("Error al eliminar la imagen: " + e.getMessage(), null));
        }
    }

    private void deleteByProducto(Context ctx) {
        try {
            int idProducto = Integer.parseInt(ctx.pathParam("id"));
            if (imgProductoService.getImgByProducto(idProducto).isEmpty()) {
                ctx.status(404).json(new Mensaje("No hay imágenes para el producto con id: " + idProducto + " para eliminar", null));
                return;
            }
            boolean eliminadas = imgProductoService.eliminarImgByProducto(idProducto);
            if (eliminadas) {
                ctx.status(200).json(new Mensaje("Imágenes del producto " + idProducto + " eliminadas", true));
            } else {
                 // Esto podría indicar un error lógico si getImgByProducto no fue empty pero no se pudo eliminar
                ctx.status(500).json(new Mensaje("Error al eliminar las imágenes del producto " + idProducto, false));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID de producto inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            // Considerar loggear la excepción
            ctx.status(500).json(new Mensaje("Error al eliminar las imágenes por producto: " + e.getMessage(), null));
        }
    }
}
