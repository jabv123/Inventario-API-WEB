package org.apirest.repository;

import java.util.List;

import org.apirest.modelo.Proveedor;
import org.apirest.service.ProveedorRepo;

public class ProveedorService {

    private final ProveedorRepo proveedorRepo;

    public ProveedorService(ProveedorRepo proveedorRepo) {
        this.proveedorRepo = proveedorRepo;
    }

    //Listar proveedores
    public List<Proveedor> listarProveedores() {
        return proveedorRepo.getProveedores();
    }

    //Listar proveedor por ID
    public Proveedor listarProveedorPorId(int id) {
        return proveedorRepo.getById(id);
    }

    //Crear proveedor
    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepo.crear(proveedor);
    }

    //Actualizar proveedor
    public Proveedor actualizarProveedor(Proveedor proveedor) {
        return proveedorRepo.actualizar(proveedor);
    }

    //Eliminar proveedor
    public boolean eliminarProveedor(int id) {
        return proveedorRepo.eliminar(id);
    }

}
