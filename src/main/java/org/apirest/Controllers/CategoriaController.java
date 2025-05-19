package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;

import org.apirest.Util.Mensaje;
import org.apirest.modelo.Categoria;
import org.apirest.service.CategoriaService;

import static io.javalin.apibuilder.ApiBuilder.*;
import java.util.List;

public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public void rutasCategorias() {
        path("/categorias", () -> {
            get(this::obtenerTodasLasCategorias);
            post(this::crearCategoria);
            path("{id}", () -> {
                get(this::obtenerCategoriaPorId);
                put(this::actualizarCategoria);
                delete(this::eliminarCategoria);
            });
        });
    }

    private void obtenerTodasLasCategorias(Context ctx) {
        List<Categoria> categorias = categoriaService.getCategorias();
        if (categorias.isEmpty()) {
            ctx.status(404).json(new Mensaje("No hay categorías registradas", null));
        } else {
            ctx.status(200).json(new Mensaje("Categorías obtenidas exitosamente", categorias));
        }
    }

    private void obtenerCategoriaPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Categoria categoria = categoriaService.getCategoria(id);
            if (categoria != null) {
                ctx.status(200).json(new Mensaje("Categoría obtenida exitosamente", categoria));
            } else {
                ctx.status(404).json(new Mensaje("Categoría no encontrada con ID: " + id, null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al obtener categoría: " + e.getMessage(), null));
        }
    }

    private void crearCategoria(Context ctx) {
        try {
            Categoria nuevaCategoria = ctx.bodyAsClass(Categoria.class);

            if (nuevaCategoria.getNombre() == null || nuevaCategoria.getNombre().isEmpty()) {
                ctx.status(400).json(new Mensaje("El nombre de la categoría es obligatorio", null));
                return;
            }
            nuevaCategoria = categoriaService.crear(nuevaCategoria);
            ctx.status(201).json(new Mensaje("Categoría creada exitosamente", nuevaCategoria));

        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al crear categoría: " + e.getMessage(), null));
        }
    }

    private void actualizarCategoria(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Categoria datosActualizacion = ctx.bodyAsClass(Categoria.class);
            Categoria categoria = categoriaService.getCategoria(id);
            if (categoria == null) {
                ctx.status(404).json(new Mensaje("Categoría no encontrada con ID: " + id, null));
                return;
            }

            if (datosActualizacion.getNombre() != null && !datosActualizacion.getNombre().isEmpty()) {
                categoria.setNombre(datosActualizacion.getNombre());
            }
            if (datosActualizacion.getDescripcion() != null) {
                categoria.setDescripcion(datosActualizacion.getDescripcion());
            }
            ctx.status(200).json(new Mensaje("Categoría actualizada exitosamente", categoria));
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al actualizar categoría: " + e.getMessage(), null));
        }
    }

    private void eliminarCategoria(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean eliminado = categoriaService.eliminar(id);
            if (eliminado) {
                ctx.status(200).json(new Mensaje("Categoría eliminada exitosamente", true));
            } else {
                ctx.status(404).json(new Mensaje("Categoría no encontrada con ID: " + id + " para eliminar", null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al eliminar categoría: " + e.getMessage(), null));
        }
    }
}