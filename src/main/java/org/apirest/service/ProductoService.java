package org.apirest.service;

import java.util.List;

import org.apirest.modelo.Producto;
import org.apirest.repository.ProductoRepo;

public class ProductoService {

    private final ProductoRepo productoRepo;

    public ProductoService(ProductoRepo productoRepo) {
        this.productoRepo = productoRepo;
    }

    //Lista todos los productos
    public List<Producto> listarProductos() {
        return productoRepo.getProductos();
    }

    //Lista producto por id
    public Producto listarProductoPorId(int id) {
        return productoRepo.getById(id);
    }

    //Crea un producto
    public Producto crearProducto(Producto producto) {
        return productoRepo.crear(producto);
    }

    //Actualiza un producto
    public Producto actualizarProducto(Producto producto) {
        return productoRepo.actualizar(producto);
    }

    //Elimina un producto por id
    public boolean eliminarProducto(int id) {
        return productoRepo.eliminar(id);
    }

    //Reduce el stock de un producto
    public Producto reducirStock(int idProducto, int cantidadAReducir) {
        Producto producto = productoRepo.getById(idProducto);
        if (producto == null) {
            throw new IllegalArgumentException("Producto con ID " + idProducto + " no encontrado.");
        }
        int nuevoStock = producto.getCantidad() - cantidadAReducir;
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("No hay suficiente stock para el producto con ID " + idProducto);
        }
        producto.setCantidad(nuevoStock);
        return productoRepo.actualizar(producto);
    }

}
