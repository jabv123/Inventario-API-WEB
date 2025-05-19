package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import org.apirest.modelo.Producto;
import org.apirest.modelo.Mensaje;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ProductoController {
    private final Map<Long, Producto> productos = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public ProductoController() {
    }

    public void rutasProductos() {
        path("/productos", () -> {
            post(this::crearProducto);
            get(this::obtenerTodosLosProductos);
            path("{id}", () -> {
                get(this::obtenerProductoPorId);
                put(this::actualizarProducto);
                delete(this::eliminarProducto);
            });
        });
    }

    private void crearProducto(Context ctx) {
        try {
            Producto producto = ctx.bodyAsClass(Producto.class);
            long id = nextId.getAndIncrement();
            producto.setId((int) id);
            productos.put(id, producto);
            ctx.status(201).json(new Mensaje("Producto agregado", producto));
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(400).json(new Mensaje("Error al crear producto: " + e.getMessage(), null));
        }
    }

    private void obtenerTodosLosProductos(Context ctx) {
        if (productos.isEmpty()){
            ctx.status(404).json(new Mensaje("No hay productos registrados", null));
        } else {
            ctx.status(200).json(new Mensaje("Lista de productos", productos.values()));
        }
    }

    private void obtenerProductoPorId(Context ctx) {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));
            Producto producto = productos.get(id);
            if (producto != null) {
                ctx.status(200).json(new Mensaje("Producto encontrado", producto));
            } else {
                ctx.status(404).json(new Mensaje("Producto no encontrado con ID: " + id, null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID de producto inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al obtener producto: " + e.getMessage(), null));
        }
    }

    private void actualizarProducto(Context ctx) {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));
            Producto productoActualizado = ctx.bodyAsClass(Producto.class);
            Producto productoExistente = productos.get(id);

            if (productoExistente != null) {
                productoActualizado.setId((int) id);
                productos.put(id, productoActualizado);
                ctx.status(200).json(new Mensaje("Producto actualizado", productoActualizado));
            } else {
                ctx.status(404).json(new Mensaje("Producto no encontrado con ID: " + id + " para actualizar", null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID de producto inválido: " + ctx.pathParam("id"), null));
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al actualizar producto: " + e.getMessage(), null));
        }
    }

    private void eliminarProducto(Context ctx) {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));
            if (productos.containsKey(id)) {
                productos.remove(id);
                ctx.status(204);
            } else {
                ctx.status(404).json(new Mensaje("Producto no encontrado con ID: " + id + " para eliminar", null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID de producto inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al eliminar producto: " + e.getMessage(), null));
        }
    }
}