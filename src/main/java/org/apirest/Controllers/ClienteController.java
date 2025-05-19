package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import org.apirest.modelo.Cliente;
import org.apirest.modelo.Mensaje;
import org.apirest.service.ClienteService;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void addClienteRoutes() {
        path("/api/clientes", () -> {
            get(this::getAll);
            post(this::create);
            path("{id}", () -> {
                get(this::getById);
                put(this::update);
                delete(this::deleteById);
            });
        });
    }

    private void getAll(Context ctx) { // Método refactorizado
        try {
            List<Cliente> clientes = clienteService.listarClientes();
            if (clientes.isEmpty()) {
                ctx.status(404).json(new Mensaje("No hay clientes registrados", null));
            } else {
                ctx.status(200).json(new Mensaje("Clientes listados correctamente", clientes));
            }
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al listar clientes: " + e.getMessage(), null));
        }
    }

    private void getById(Context ctx) { // Método refactorizado
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Cliente cliente = clienteService.listarClientePorId(id);
            if (cliente == null) {
                ctx.status(404).json(new Mensaje("Cliente no encontrado", null));
            } else {
                ctx.status(200).json(new Mensaje("Cliente listado correctamente", cliente));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al listar cliente: " + e.getMessage(), null));
        }
    }

    private void create(Context ctx) { // Método refactorizado
        try {
            Cliente cliente = ctx.bodyAsClass(Cliente.class);
            Cliente nuevoCliente = clienteService.crearCliente(cliente);
            ctx.status(201).json(new Mensaje("Cliente creado correctamente", nuevoCliente));
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al crear cliente: " + e.getMessage(), null));
        }
    }

    private void update(Context ctx) { // Método refactorizado
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Cliente clienteActualizar = ctx.bodyAsClass(Cliente.class);

            if (clienteService.listarClientePorId(id) == null) {
                ctx.status(404).json(new Mensaje("Cliente no encontrado con ID: " + id + " para actualizar", null));
                return;
            }
            clienteActualizar.setIdCliente(id); // Asegurar que el ID en el cuerpo coincida o se use el de la URL
            Cliente clienteActualizado = clienteService.actualizarCliente(clienteActualizar);
            ctx.status(200).json(new Mensaje("Cliente actualizado correctamente", clienteActualizado));
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al actualizar cliente: " + e.getMessage(), null));
        }
    }

    private void deleteById(Context ctx) { // Método refactorizado
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if (clienteService.listarClientePorId(id) == null) {
                ctx.status(404).json(new Mensaje("Cliente no encontrado con ID: " + id + " para eliminar", null));
                return;
            }
            boolean eliminado = clienteService.eliminarCliente(id);
            if (eliminado) {
                ctx.status(200).json(new Mensaje("Cliente eliminado correctamente", true));
            } else {
                ctx.status(500).json(new Mensaje("Error al eliminar el cliente con ID: " + id, false));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al eliminar cliente: " + e.getMessage(), null));
        }
    }
}
