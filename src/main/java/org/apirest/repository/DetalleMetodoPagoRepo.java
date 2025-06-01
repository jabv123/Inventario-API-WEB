package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.DetalleMetodoPago;

public class DetalleMetodoPagoRepo {

    private final List<DetalleMetodoPago> detalles = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    public DetalleMetodoPago save (DetalleMetodoPago detalle) {
        detalle.setId(id.getAndIncrement());
        detalles.add(detalle);
        return detalle;
    }

    public DetalleMetodoPago findById(int id) {
        return detalles.stream()
                .filter(detalle -> detalle.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<DetalleMetodoPago> findByMetodoPago(int idMetodoPago) {
        return detalles.stream()
                .filter(detalle -> detalle.getIdMetodoPago() == idMetodoPago)
                .toList();
    }

    public boolean deleteByMetodoPago(int idMetodoPago) {
        return detalles.removeIf(detalle -> detalle.getIdMetodoPago() == idMetodoPago);
    }

}
