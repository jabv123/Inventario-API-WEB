package org.apirest.Controllers;

import static spark.Spark.*;

import org.apirest.modelo.Cliente;
import org.apirest.modelo.Mensaje;
import org.apirest.service.ClienteService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClienteController {

    private final ClienteService clienteService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
        this.rutasClientes();
    }

    private void rutasClientes(){

        path("/api/clientes", () -> {

            //Listar todos los clientes
            get("", (req, res) -> {
                res.type("application/json");
                
                try {
                    res.status(200);
                    // Listar todos los clientes
                    return objectMapper.writeValueAsString(new Mensaje("Clientes listados correctamente"));
                } catch (Exception e) {
                    res.status(500);
                    return "Error al listar clientes: " + e.getMessage();
                }
            });

            //Listar cliente por id
            get("/:id", (req, res) -> {
                res.type("application/json");
                
                try {
                    // Validar el ID del cliente
                    int id = Integer.parseInt(req.params(":id"));
                    Cliente existeCliente = clienteService.listarClientePorId(id);
                    if (existeCliente == null) {
                        res.status(404);
                        return "Cliente no encontrado";
                    }
                    res.status(200);
                    // Listar cliente por id
                    return objectMapper.writeValueAsString(new Mensaje("Cliente listado correctamente"));
                } catch (Exception e) {
                    res.status(500);
                    return "Error al listar cliente: " + e.getMessage();
                }
            });


            //Crear cliente
            post("", (req, res) -> {
                res.type("application/json");
                
                try {
                    Cliente cliente = objectMapper.readValue(req.body(), Cliente.class);
                    return objectMapper.writeValueAsString(new Mensaje("Cliente creado correctamente"));
                } catch (Exception e) {
                    res.status(500);
                    return "Error al crear cliente: " + e.getMessage();
                }
            });

            //Actualizar cliente
            put("/:idCliente", (req, res) -> {
                res.type("application/json");
                
                try {
                    // Validar el ID del cliente
                    int id = Integer.parseInt(req.params(":idCliente"));
                    
                    Cliente existeCliente = clienteService.listarClientePorId(id);
                    if (existeCliente == null) {
                        res.status(404);
                        return "Cliente no encontrado";
                    }

                    res.status(200);
                    // Actualizar cliente
                    Cliente cliente = objectMapper.readValue(req.body(), Cliente.class);
                    cliente.setIdCliente(id); // Asegurarse de que el ID del cliente sea el correcto
                    return objectMapper.writeValueAsString(new Mensaje("Cliente actualizado correctamente"));
                } catch (Exception e) {
                    res.status(500);
                    return "Error al actualizar cliente: " + e.getMessage();
                }
            });

            //Eliminar cliente por id
            delete("/:idCliente", (req, res) -> {
                res.type("application/json");
                
                try {
                    
                    // Validar el ID del cliente
                    int id = Integer.parseInt(req.params(":idCliente"));
                    Cliente existeCliente = clienteService.listarClientePorId(id);
                    if (existeCliente == null) {
                        res.status(404);
                        return "Cliente no encontrado";
                    }

                    res.status(200);
                    // Eliminar cliente por id
                    
                    return objectMapper.writeValueAsString(new Mensaje("Cliente eliminado correctamente"));
                } catch (Exception e) {
                    res.status(500);
                    return "Error al eliminar cliente: " + e.getMessage();
                }
            });

        });

    }

}
