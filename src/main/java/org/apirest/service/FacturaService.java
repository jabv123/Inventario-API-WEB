package org.apirest.service;

import org.apirest.modelo.Factura;
import org.apirest.repository.FacturaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class FacturaService {
    private static FacturaService instance;
    private final FacturaRepository facturaRepository;

    private FacturaService() {
        this.facturaRepository = FacturaRepository.getInstance();
    }

    public static FacturaService getInstance() {
        if (instance == null) {
            instance = new FacturaService();
        }
        return instance;
    }

    public Factura crearFactura(Factura factura) {
        // Generar número de factura automático si no se proporciona
        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isEmpty()) {
            factura.setNumeroFactura("FAC-" + System.currentTimeMillis());
        }
        
        // Establecer fecha actual si no se proporciona
        if (factura.getFechaFactura() == null) {
            factura.setFechaFactura(LocalDateTime.now());
        }
        
        return facturaRepository.save(factura);
    }

    public Optional<Factura> obtenerFacturaPorId(String id) {
        return facturaRepository.findById(id);
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public List<Factura> obtenerFacturasPorVenta(String idVenta) {
        return facturaRepository.findByIdVenta(idVenta);
    }

    public Optional<Factura> actualizarFactura(String id, Factura facturaActualizada) {
        Optional<Factura> facturaExistente = facturaRepository.findById(id);
        if (facturaExistente.isPresent()) {
            return Optional.of(facturaRepository.update(id, facturaActualizada));
        }
        return Optional.empty();
    }

    public boolean eliminarFactura(String id) {
        return facturaRepository.deleteById(id);
    }

}
