package org.apirest.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.Venta;

public class VentaRepo {

    private final List<Venta> ventas = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    // Obtener todas las ventas
    public List<Venta> getAll() {
        return ventas;
    }

    // Obtener venta por id
    public Venta getById(int id) {
        return ventas.stream()
                .filter(venta -> venta.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Obtener ventas por id de cliente
    public List<Venta> getByClientId(int idCliente) {
        return ventas.stream()
                .filter(venta -> venta.getIdCliente() == idCliente)
                .toList();
    }

    // Obtener ventas por estado
    public List<Venta> getByEstado(String estado) {
        return ventas.stream()
                .filter(venta -> venta.getEstado().equalsIgnoreCase(estado))
                .toList();
    }

    // Crear venta
    public Venta add(Venta venta) {
        venta.setId(id.getAndIncrement());
        venta.setFechaVenta(LocalDate.now());
        // venta.setDetalles(new ArrayList<>()); // Comentado para no sobrescribir detalles existentes si ya vienen informados
        ventas.add(venta);
        return venta;
    }

    // Actualizar venta
    public Venta update(Venta venta) {
        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            if (v.getId() == venta.getId()) {
                // Actualizar solo los campos que no son nulos en la venta entrante
                if (venta.getIdCliente() != 0) {
                    v.setIdCliente(venta.getIdCliente());
                }
                if (venta.getEstado() != null) {
                    v.setEstado(venta.getEstado());
                }
                if (venta.getTotal() != 0) {
                    v.setTotal(venta.getTotal());
                }
                v.setDetalles(venta.getDetalles());
                return v;
            }
        }
        return null;
    }

    // Eliminar venta
    public boolean delete(int id) {
        return ventas.removeIf(venta -> venta.getId() == id);
    }
}
