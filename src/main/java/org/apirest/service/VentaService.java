package org.apirest.service;

import java.util.List;
import java.util.ArrayList;

import org.apirest.modelo.Venta;
import org.apirest.modelo.Carrito;
import org.apirest.modelo.DetalleVenta;
import org.apirest.modelo.ItemCarrito;
import org.apirest.modelo.Producto;
import org.apirest.repository.DetalleVentaRepo;
import org.apirest.repository.VentaRepo;

public class VentaService {

    private final VentaRepo ventaRepository;
    private final DetalleVentaRepo detalleVentaRepository;
    private final CarritoService carritoService;
    private final ProductoService productoService;

    public VentaService(VentaRepo ventaRepository, DetalleVentaRepo detalleVentaRepository, CarritoService carritoService, ProductoService productoService) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
        this.carritoService = carritoService;
        this.productoService = productoService;
    }

    public Venta realizarVenta(Venta venta) {
        /* *
         * Desde el controlador se obtiene el cliente.
         * Para obtener los detalles hacemos uso de servicio de carrito para obtenerlo 
         * Con esto completamos los detalles de la venta
         */

         //TODO: Implementar la reducción de stock de productos
        // Obtener el cliente desde la venta
        int idCliente = venta.getIdCliente();
        // Obtener carrito del cliente
        Carrito carrito = carritoService.getCarritoByIdCliente(idCliente);

        if (carrito == null || carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío o no existe.");
        }

        List<DetalleVenta> detallesVenta = new ArrayList<>();
        double totalVenta = 0;

        for (ItemCarrito itemCarrito : carrito.getItems()) {
            Producto producto = productoService.listarProductoPorId(itemCarrito.getIdProducto());
            if (producto == null) {
                throw new IllegalArgumentException("Producto con ID " + itemCarrito.getIdProducto() + " no encontrado.");
            }

            DetalleVenta detalle = new DetalleVenta();
            detalle.setIdProducto(itemCarrito.getIdProducto());
            detalle.setCantidad(itemCarrito.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(itemCarrito.getCantidad() * producto.getPrecio());

            detallesVenta.add(detalle);
            totalVenta += detalle.getSubtotal();
        }

        venta.setDetalles(detallesVenta);
        venta.setTotal(totalVenta);

        Venta ventaGuardada = ventaRepository.add(venta);

        for (DetalleVenta detalle : ventaGuardada.getDetalles()) {
            detalle.setIdVenta(ventaGuardada.getId());
            detalleVentaRepository.add(detalle);
        }

        carritoService.eliminarCarritoPorIdCliente(idCliente);

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
