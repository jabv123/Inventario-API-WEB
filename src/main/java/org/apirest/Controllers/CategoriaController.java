package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;

import org.apirest.Util.Mensaje;
import org.apirest.modelo.Categoria;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaController {

    private List<Categoria> categorias = new ArrayList<>();
    private int nextId = 1;

    public CategoriaController() {
        // La inicialización de rutas se hará a través de un método público
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
        if (categorias.isEmpty()) {
            ctx.status(404).json(new Mensaje("No hay categorías registradas", null));
        } else {
            ctx.status(200).json(new Mensaje("Categorías obtenidas exitosamente", categorias));
        }
    }

    private void obtenerCategoriaPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Optional<Categoria> categoriaOpt = categorias.stream().filter(c -> c.getId() == id).findFirst();
            if (categoriaOpt.isPresent()) {
                ctx.status(200).json(new Mensaje("Categoría obtenida exitosamente", categoriaOpt.get()));
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
            nuevaCategoria.setId(nextId++);
            categorias.add(nuevaCategoria);
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
            Optional<Categoria> categoriaOpt = categorias.stream().filter(c -> c.getId() == id).findFirst();

            if (categoriaOpt.isPresent()) {
                Categoria categoria = categoriaOpt.get();
                if (datosActualizacion.getNombre() != null && !datosActualizacion.getNombre().isEmpty()) {
                    categoria.setNombre(datosActualizacion.getNombre());
                }
                if (datosActualizacion.getDescripcion() != null) {
                    categoria.setDescripcion(datosActualizacion.getDescripcion());
                }
                ctx.status(200).json(new Mensaje("Categoría actualizada exitosamente", categoria));
            } else {
                ctx.status(404).json(new Mensaje("Categoría no encontrada con ID: " + id + " para actualizar", null));
            }
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
            boolean eliminado = categorias.removeIf(categoria -> categoria.getId() == id);
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