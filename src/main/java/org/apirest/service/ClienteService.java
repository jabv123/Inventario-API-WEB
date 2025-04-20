package org.apirest.service;

import java.util.List;

import org.apirest.modelo.Cliente;
import org.apirest.repository.ClienteRepo;

public class ClienteService {

    private final ClienteRepo clienteRepo;

    public ClienteService(ClienteRepo clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    //Lista todos clientes
    public List<Cliente> listarClientes() {
        return clienteRepo.getClientes();
    }


    //Lista cliente por id
    public Cliente listarClientePorId(int id) {
        return clienteRepo.getById(id);
    }

    //Crea un cliente
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepo.crear(cliente);
    }

    //Actualiza un cliente
    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepo.actualizar(cliente);
    }

    //Elimina un cliente por id
    public Cliente eliminarCliente(int id) {
        return clienteRepo.eliminar(id);
    }

}
