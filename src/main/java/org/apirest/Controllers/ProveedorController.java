package org.apirest.Controllers;

import org.apirest.modelo.Mensaje;
import org.apirest.modelo.Proveedor;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProveedorController {

    private static List<Proveedor> proveedores = new ArrayList<>();
    private static int nextId = 1;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public ProveedorController() {
        path("/proveedores", () -> {
            get("", (req, res) -> obtenerTodosLosProveedores(req, res));
            get("/:id", (req, res) -> obtenerProveedorPorId(req, res));
            post("", (req, res) -> crearProveedor(req, res));
            put("/:id", (req, res) -> actualizarProveedor(req, res));
            delete("/:id", (req, res) -> eliminarProveedor(req, res));
        });
    }

    private String obtenerTodosLosProveedores(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            return objectMapper.writeValueAsString(proveedores);
        } catch (Exception e) {
            res.status(500);
            return writeValueAsString(new Mensaje("Error al obtener proveedores: " + e.getMessage()));
        }
    }

    private String obtenerProveedorPorId(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            for (Proveedor proveedor : proveedores) {
                if (proveedor.getId() == id) {
                    return objectMapper.writeValueAsString(proveedor);
                }
            }
            res.status(404);
            return writeValueAsString(new Mensaje("Proveedor no encontrado"));
        } catch (NumberFormatException e) {
            res.status(400);
            return writeValueAsString(new Mensaje("ID inválido"));
        } catch (Exception e) {
            res.status(500);
            return writeValueAsString(new Mensaje("Error al obtener proveedor: " + e.getMessage()));
        }
    }

    private String crearProveedor(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            Proveedor nuevoProveedor = objectMapper.readValue(req.body(), Proveedor.class);

            if (nuevoProveedor.getNombre() == null || nuevoProveedor.getNombre().isEmpty()) {
                res.status(400);
                return writeValueAsString(new Mensaje("El nombre del proveedor es obligatorio"));
            }

            nuevoProveedor.setId((long) nextId++);
            proveedores.add(nuevoProveedor);
            res.status(201);
            return writeValueAsString(new Mensaje("Proveedor creado exitosamente", nuevoProveedor));

        } catch (Exception e) {
            res.status(400);
            return writeValueAsString(new Mensaje("Solicitud inválida o error al crear proveedor: " + e.getMessage()));
        }
    }

    private String actualizarProveedor(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            Proveedor datosActualizacion = objectMapper.readValue(req.body(), Proveedor.class);

            for (Proveedor proveedor : proveedores) {
                if (proveedor.getId() == id) {
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
                    return writeValueAsString(new Mensaje("Proveedor actualizado exitosamente", proveedor));
                }
            }
            res.status(404);
            return writeValueAsString(new Mensaje("Proveedor no encontrado"));

        } catch (NumberFormatException e) {
            res.status(400);
            return writeValueAsString(new Mensaje("ID inválido"));
        } catch (Exception e) {
            res.status(400);
            return writeValueAsString(new Mensaje("Solicitud inválida o error al actualizar proveedor: " + e.getMessage()));
        }
    }

    private String eliminarProveedor(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            int id = Integer.parseInt(req.params(":id"));
            boolean removed = proveedores.removeIf(proveedor -> proveedor.getId() == id);
            if (removed) {
                return writeValueAsString(new Mensaje("Proveedor eliminado exitosamente"));
            } else {
                res.status(404);
                return writeValueAsString(new Mensaje("Proveedor no encontrado para eliminar"));
            }
        } catch (NumberFormatException e) {
            res.status(400);
            return writeValueAsString(new Mensaje("ID inválido"));
        } catch (Exception e) {
            res.status(500);
            return writeValueAsString(new Mensaje("Error al eliminar proveedor: " + e.getMessage()));
        }
    }

    private String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            return "{\"mensaje\":\"Error interno al serializar la respuesta\"}";
        }
    }
}