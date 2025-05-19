package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.Producto;

public class ProductoRepo {

    private final List<Producto> productos = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    public List<Producto> getProductos() {
        return productos;
    }

    public Producto getById(int id) {
        return productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }


    public Producto crear(Producto producto) {
        producto.setId(id.getAndIncrement());
        productos.add(producto);
        return producto;
    }

    public Producto actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            if (p.getId() == producto.getId()) {
                productos.set(i, producto);
                return producto;
            }
        }
        return null;
    }

    public boolean eliminar(int idProducto) {
        return productos.removeIf(producto -> producto.getId() == idProducto);
    }
}