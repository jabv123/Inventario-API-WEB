package org.apirest.service;

import java.util.List;

import org.apirest.modelo.Venta;
import org.apirest.modelo.DetalleVenta;
import org.apirest.repository.DetalleVentaRepo;
import org.apirest.repository.VentaRepo;

public class VentaService {

    private final VentaRepo ventaRepository;
    private final DetalleVentaRepo detalleVentaRepository;

    public VentaService(VentaRepo ventaRepository, DetalleVentaRepo detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
    }

    public Venta realizarVenta(Venta venta) {
        // Validar que la venta tenga detalles
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un detalle.");
        }

        // Calcular el total de la venta y asignar idVenta a cada detalle
        double totalVenta = 0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            // Añadir lógica para verificar stock de producto si es necesario
            // y calcular el subtotal del detalle si no viene calculado
            if (detalle.getSubtotal() == 0 && detalle.getCantidad() > 0 && detalle.getPrecioUnitario() > 0) {
                detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
            }
            totalVenta += detalle.getSubtotal();
            // Asignar el id de la venta al detalle (se hará después de guardar la venta)
        }
        venta.setTotal(totalVenta);

        // Guardar la venta para obtener su ID
        Venta ventaGuardada = ventaRepository.add(venta);

        // Asignar el ID de la venta guardada a cada detalle y guardarlos
        for (DetalleVenta detalle : ventaGuardada.getDetalles()) {
            detalle.setIdVenta(ventaGuardada.getId());
            detalleVentaRepository.add(detalle); // Guardar cada detalle
        }

        return ventaGuardada;
    }

    public Venta actualizarVenta(Venta venta) {
        return ventaRepository.update(venta);
    }

    public boolean eliminarVenta(int id) {
        return ventaRepository.delete(id);
    }

    public Venta obtenerVentaPorId(int id) {
        return ventaRepository.getById(id);
    }

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.getAll();
    }

    public List<Venta> obtenerVentasPorCliente(int idCliente) {
        return ventaRepository.getByClientId(idCliente);
    }

    public List<Venta> obtenerVentasPorEstado(String estado) {
        return ventaRepository.getByEstado(estado);
    }

}
