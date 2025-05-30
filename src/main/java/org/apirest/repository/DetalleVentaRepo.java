package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.DetalleVenta;

public class DetalleVentaRepo {
    private final List<DetalleVenta> detalles = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    // Obtener todos los detalles de venta
    public List<DetalleVenta> getAll() {
        return detalles;
    }

    // Obtener detalle de venta por id
    public DetalleVenta getById(int id) {
        return detalles.stream()
                .filter(detalle -> detalle.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Crear detalle de venta
    public DetalleVenta add(DetalleVenta detalle) {
        detalle.setId(id.getAndIncrement());
        detalles.add(detalle);
        return detalle;
    }

    // Actualizar detalle de venta
    public DetalleVenta update(DetalleVenta detalle) {
        for (int i = 0; i < detalles.size(); i++) {
            DetalleVenta d = detalles.get(i);
            if (d.getId() == detalle.getId()) {
                // Actualizar solo los campos que no son nulos en el detalle entrante
                if (detalle.getIdProducto() != 0) {
                    d.setIdProducto(detalle.getIdProducto());
                }
                if (detalle.getCantidad() != 0) {
                    d.setCantidad(detalle.getCantidad());
                }
                if (detalle.getPrecioUnitario() != 0) {
                    d.setPrecioUnitario(detalle.getPrecioUnitario());
                }
                if (detalle.getSubtotal() != 0) {
                    d.setSubtotal(detalle.getSubtotal());
                }
                return d;
            }
        }
        return null;
    }

    // Eliminar detalle de venta
    public boolean delete(int id) {
        return detalles.removeIf(detalle -> detalle.getId() == id);
    }
}
