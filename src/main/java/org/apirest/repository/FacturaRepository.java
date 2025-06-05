package org.apirest.repository;

import org.apirest.modelo.Factura;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FacturaRepository {
    private final List<Factura> facturas = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger idCounter = new AtomicInteger(1); // Inicia en 1 para evitar el ID 0

    public Factura save(Factura factura) {
        factura.setId(idCounter.getAndIncrement()); // Asigna un ID único
        facturas.add(factura);
        return factura;
    }

    public Optional<Factura> findById(int id) {
        return facturas.stream().filter(factura -> factura.getId() == id).findFirst();
    }

    public List<Factura> findAll() {
        return new ArrayList<>(facturas);
    }

    public List<Factura> findByIdVenta(int idVenta) {
        return facturas.stream()
                .filter(factura -> factura.getIdVenta() == idVenta)
                .toList();
    }

    public boolean deleteById(int id) {
        return facturas.removeIf(factura -> factura.getId() == id);
    }

    public Factura update(int id, Factura facturaActualizada) {
        Optional<Factura> facturaExistente = findById(id);
        if (facturaExistente.isPresent()) {
            facturaActualizada.setId(id);
            facturas.remove(facturaExistente.get());
            facturas.add(facturaActualizada);
            return facturaActualizada;
        }
        return null;
    }

}
