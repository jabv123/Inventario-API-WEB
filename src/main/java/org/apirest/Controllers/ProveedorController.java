package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import org.apirest.modelo.Mensaje;
import org.apirest.modelo.Proveedor;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProveedorController {

    private List<Proveedor> proveedores = new ArrayList<>();
    private int nextId = 1;

    public ProveedorController() {
    }

    public void rutasProveedores() {
        path("/proveedores", () -> {
            get(this::obtenerTodosLosProveedores);
            post(this::crearProveedor);
            path("{id}", () -> {
                get(this::obtenerProveedorPorId);
                put(this::actualizarProveedor);
                delete(this::eliminarProveedor);
            });
        });
    }

    private void obtenerTodosLosProveedores(Context ctx) {
        if (proveedores.isEmpty()) {
            ctx.status(404).json(new Mensaje("No hay proveedores registrados", null));
        } else {
            ctx.status(200).json(new Mensaje("Proveedores listados correctamente", proveedores));
        }
    }

    private void obtenerProveedorPorId(Context ctx) {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));
            Optional<Proveedor> proveedorOpt = proveedores.stream().filter(p -> p.getId() == id).findFirst();
            if (proveedorOpt.isPresent()) {
                ctx.status(200).json(new Mensaje("Proveedor encontrado", proveedorOpt.get()));
            } else {
                ctx.status(404).json(new Mensaje("Proveedor no encontrado con ID: " + id, null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al obtener proveedor: " + e.getMessage(), null));
        }
    }

    private void crearProveedor(Context ctx) {
        try {
            Proveedor nuevoProveedor = ctx.bodyAsClass(Proveedor.class);

            if (nuevoProveedor.getNombre() == null || nuevoProveedor.getNombre().isEmpty()) {
                ctx.status(400).json(new Mensaje("El nombre del proveedor es obligatorio", null));
                return;
            }

            nuevoProveedor.setId((long) nextId++);
            proveedores.add(nuevoProveedor);
            ctx.status(201).json(new Mensaje("Proveedor creado exitosamente", nuevoProveedor));

        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al crear proveedor: " + e.getMessage(), null));
        }
    }

    private void actualizarProveedor(Context ctx) {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));
            Proveedor datosActualizacion = ctx.bodyAsClass(Proveedor.class);
            Optional<Proveedor> proveedorOpt = proveedores.stream().filter(p -> p.getId() == id).findFirst();

            if (proveedorOpt.isPresent()) {
                Proveedor proveedor = proveedorOpt.get();
                if (datosActualizacion.getNombre() != null && !datosActualizacion.getNombre().isEmpty()) {
                    proveedor.setNombre(datosActualizacion.getNombre());
                }
                if (datosActualizacion.getContacto() != null && !datosActualizacion.getContacto().isEmpty()) {
                    proveedor.setContacto(datosActualizacion.getContacto());
                }
                if (datosActualizacion.getTelefono() != null && !datosActualizacion.getTelefono().isEmpty()) {
                    proveedor.setTelefono(datosActualizacion.getTelefono());
                }
                if (datosActualizacion.getEmail() != null && !datosActualizacion.getEmail().isEmpty()) {
                    proveedor.setEmail(datosActualizacion.getEmail());
                }
                if (datosActualizacion.getDireccion() != null && !datosActualizacion.getDireccion().isEmpty()) {
                    proveedor.setDireccion(datosActualizacion.getDireccion());
                }
                ctx.status(200).json(new Mensaje("Proveedor actualizado exitosamente", proveedor));
            } else {
                ctx.status(404).json(new Mensaje("Proveedor no encontrado con ID: " + id + " para actualizar", null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al actualizar proveedor: " + e.getMessage(), null));
        }
    }

    private void eliminarProveedor(Context ctx) {
        try {
            long id = Long.parseLong(ctx.pathParam("id"));
            boolean removed = proveedores.removeIf(proveedor -> proveedor.getId() == id);
            if (removed) {
                ctx.status(200).json(new Mensaje("Proveedor eliminado exitosamente", true));
            } else {
                ctx.status(404).json(new Mensaje("Proveedor no encontrado con ID: " + id + " para eliminar", null));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al eliminar proveedor: " + e.getMessage(), null));
        }
    }
}