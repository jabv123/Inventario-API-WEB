package org.apirest.Controllers;

import org.apirest.modelo.Proveedor;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

public class ProveedorController {

    private static List<Proveedor> proveedores = new ArrayList<>();
    private static int nextId = 1;

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
        List<Map<String, Object>> proveedoresJson = new ArrayList<>();
        for (Proveedor proveedor : proveedores) {
            Map<String, Object> proveedorMap = new HashMap<>();
            proveedorMap.put("id", proveedor.getId());
            proveedorMap.put("nombre", proveedor.getNombre());
            proveedorMap.put("contacto", proveedor.getContacto());
            proveedorMap.put("telefono", proveedor.getTelefono());
            proveedorMap.put("email", proveedor.getEmail());
            proveedorMap.put("direccion", proveedor.getDireccion());
            proveedoresJson.add(proveedorMap);
        }
        return new JSONArray(proveedoresJson).toString();
    }

    private String obtenerProveedorPorId(spark.Request req, spark.Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getId() == id) {
                Map<String, Object> proveedorMap = new HashMap<>();
                proveedorMap.put("id", proveedor.getId());
                proveedorMap.put("nombre", proveedor.getNombre());
                proveedorMap.put("contacto", proveedor.getContacto());
                proveedorMap.put("telefono", proveedor.getTelefono());
                proveedorMap.put("email", proveedor.getEmail());
                proveedorMap.put("direccion", proveedor.getDireccion());
                return new JSONObject(proveedorMap).toString();
            }
        }
        res.status(404);
        return new JSONObject(Map.of("mensaje", "Proveedor no encontrado")).toString();
    }

    private String crearProveedor(spark.Request req, spark.Response res) {
        res.type("application/json");
        try {
            JSONObject jsonObject = new JSONObject(req.body());
            String nombre = jsonObject.getString("nombre");
            String contacto = jsonObject.getString("contacto");
            String telefono = jsonObject.getString("telefono");
            String email = jsonObject.getString("email");
            String direccion = jsonObject.getString("direccion");

            Proveedor nuevoProveedor = new Proveedor(nombre, contacto, telefono, email, direccion);
            nuevoProveedor.setId((long) nextId++);
            proveedores.add(nuevoProveedor);
            res.status(201);
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Proveedor creado exitosamente");
            respuesta.put("id", nuevoProveedor.getId());
            return new JSONObject(respuesta).toString();

        } catch (Exception e) {
            res.status(400);
            return new JSONObject(Map.of("mensaje", "Solicitud inválida")).toString();
        }
    }

    private String actualizarProveedor(spark.Request req, spark.Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        try {
            JSONObject jsonObject = new JSONObject(req.body());
            String nombre = jsonObject.optString("nombre");
            String contacto = jsonObject.optString("contacto");
            String telefono = jsonObject.optString("telefono");
            String email = jsonObject.optString("email");
            String direccion = jsonObject.optString("direccion");

            for (Proveedor proveedor : proveedores) {
                if (proveedor.getId() == id) {
                    if (nombre != null && !nombre.isEmpty()) {
                        proveedor.setNombre(nombre);
                    }
                    if (contacto != null && !contacto.isEmpty()) {
                        proveedor.setContacto(contacto);
                    }
                    if (telefono != null && !telefono.isEmpty()) {
                        proveedor.setTelefono(telefono);
                    }
                    if (email != null && !email.isEmpty()) {
                        proveedor.setEmail(email);
                    }
                    if (direccion != null && !direccion.isEmpty()) {
                        proveedor.setDireccion(direccion);
                    }
                    return new JSONObject(Map.of("mensaje", "Proveedor actualizado exitosamente")).toString();
                }
            }
            res.status(404);
            return new JSONObject(Map.of("mensaje", "Proveedor no encontrado")).toString();

        } catch (Exception e) {
            res.status(400);
            return new JSONObject(Map.of("mensaje", "Solicitud inválida")).toString();
        }
    }

    private String eliminarProveedor(spark.Request req, spark.Response res) {
        res.type("application/json");
        int id = Integer.parseInt(req.params(":id"));
        proveedores.removeIf(proveedor -> proveedor.getId() == id);
        return new JSONObject(Map.of("mensaje", "Proveedor eliminado exitosamente")).toString();
    }

    public static void main(String[] args) {
        new ProveedorController();
    }
}