package org.apirest.repository;

import org.apirest.modelo.Factura;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FacturaRepository {
    private static FacturaRepository instance;
    private final Map<String, Factura> facturas;
    private final AtomicInteger idCounter;

    private FacturaRepository() {
        this.facturas = new ConcurrentHashMap<>();
        this.idCounter = new AtomicInteger(1);
    }

    public static FacturaRepository getInstance() {
        if (instance == null) {
            instance = new FacturaRepository();
        }
        return instance;
    }

    public Factura save(Factura factura) {
        if (factura.getId() == null || factura.getId().isEmpty()) {
            factura.setId(String.valueOf(idCounter.getAndIncrement()));
        }
        facturas.put(factura.getId(), factura);
        return factura;
    }

    public Optional<Factura> findById(String id) {
        return Optional.ofNullable(facturas.get(id));
    }

    public List<Factura> findAll() {
        return new ArrayList<>(facturas.values());
    }

    public List<Factura> findByIdVenta(String idVenta) {
        return facturas.values().stream()
                .filter(factura -> factura.getIdVenta().equals(idVenta))
                .toList();
    }

    public boolean deleteById(String id) {
        return facturas.remove(id) != null;
    }

    public Factura update(String id, Factura facturaActualizada) {
        if (facturas.containsKey(id)) {
            facturaActualizada.setId(id);
            facturas.put(id, facturaActualizada);
            return facturaActualizada;
        }
        return null;
    }

}
