package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apirest.modelo.Proveedor;

public class ProveedorRepo {

    private final List<Proveedor> proveedores = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong id = new AtomicLong(1);

    // Obtener todos los proveedores
    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    // Obtener proveedor por ID
    public Proveedor getById(long id) {
        return proveedores.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    // Crear un nuevo proveedor
    public Proveedor crear(Proveedor proveedor) {
        proveedor.setId(id.getAndIncrement());
        proveedores.add(proveedor);
        return proveedor;
    }

    // Actualizar un proveedor existente
    public Proveedor actualizar(Proveedor proveedor) {
        Proveedor prov = getById(proveedor.getId());
        if (prov != null) {
            prov.setNombre(proveedor.getNombre());
            prov.setDireccion(proveedor.getDireccion());
            prov.setTelefono(proveedor.getTelefono());
        }
        return prov;
    }

    // Eliminar un proveedor
    public boolean eliminar(long id) {
        Proveedor proveedor = getById(id);
        if (proveedor != null) {
            proveedores.remove(proveedor);
            return true;
        }
        return false;
    }

}
