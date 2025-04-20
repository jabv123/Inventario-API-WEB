package org.apirest.repository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.Cliente;

public class ClienteRepo {

    private final List<Cliente> clientes = Collections.synchronizedList(new java.util.ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    //Listar todos los clientes
    public List<Cliente> getClientes() {
        return clientes;
    }


    //Listar por id
    public Cliente getById(int id) {
        return clientes.stream().filter(cliente -> cliente.getIdCliente() == id).findFirst().orElse(null);
    }

    //Crear cliente
    public Cliente crear(Cliente cliente) {
        cliente.setIdCliente(id.getAndIncrement());
        clientes.add(cliente);
        return cliente;
    }

    //Actualizar cliente
    public Cliente actualizar (Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            if (c.getIdCliente() == cliente.getIdCliente()) {
                clientes.set(i, cliente);
                return cliente;
            }
        }
        return null;
    }

    //Eliminar cliente
    public Cliente eliminar(int idCliente){
        return clientes.removeIf(cliente -> cliente.getIdCliente() == idCliente) ? getById(idCliente) : null;
    }
    

}
