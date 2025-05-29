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
    private final CuponDescuentoService cuponDescuentoService;

    public VentaService(VentaRepo ventaRepository, DetalleVentaRepo detalleVentaRepository, CarritoService carritoService, ProductoService productoService, CuponDescuentoService cuponDescuentoService) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.cuponDescuentoService = cuponDescuentoService;
    }

    public Venta realizarVenta(Venta venta) {
        /* *
         * Desde el controlador se obtiene el cliente.
         * Para obtener los detalles hacemos uso de servicio de carrito para obtenerlo 
         * Con esto completamos los detalles de la venta
         */

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
        }        venta.setDetalles(detallesVenta);
        
        // Guardar el total sin descuento
        venta.setTotalSinDescuento(totalVenta);
        
        // Aplicar cupón si existe
        double totalFinal = aplicarCuponSiExiste(venta, totalVenta);
        venta.setTotal(totalFinal);

        Venta ventaGuardada = ventaRepository.add(venta);

        for (DetalleVenta detalle : ventaGuardada.getDetalles()) {
            detalle.setIdVenta(ventaGuardada.getId());
            detalleVentaRepository.add(detalle);
        }

        carritoService.eliminarCarritoPorIdCliente(idCliente);
        // Actualizar el stock de los productos vendidos
        for (ItemCarrito itemCarrito : carrito.getItems()) {
            productoService.reducirStock(itemCarrito.getIdProducto(), itemCarrito.getCantidad());
        }

        return ventaGuardada;
    }

    //TODO: Recomendacion, implementar un metodo para actulizar stock de un producto, no solo reducirlo si no aumentarlo en caso de devoluciones
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

    // === MÉTODOS PARA MANEJO DE CUPONES ===

    /**
     * Aplica un cupón a la venta si existe un código de cupón válido
     */
    private double aplicarCuponSiExiste(Venta venta, double totalBase) {
        String codigoCupon = venta.getCodigoCuponAplicado();
        
        // Si no hay código de cupón, retornar el total base
        if (codigoCupon == null || codigoCupon.trim().isEmpty()) {
            venta.setDescuentoAplicado(0);
            return totalBase;
        }
        
        try {
            // Validar y calcular descuento
            if (!cuponDescuentoService.validarCupon(codigoCupon)) {
                throw new IllegalArgumentException("Cupón inválido o expirado: " + codigoCupon);
            }
            
            double descuento = cuponDescuentoService.calcularDescuento(codigoCupon, totalBase);
            double totalConDescuento = totalBase - descuento;
            
            // Guardar información del descuento aplicado
            venta.setDescuentoAplicado(descuento);
            
            return totalConDescuento;
            
        } catch (Exception e) {
            // Si hay error con el cupón, lanzar excepción
            throw new IllegalArgumentException("Error al aplicar cupón: " + e.getMessage());
        }
    }

    /**
     * Realiza una venta con cupón específico
     */
    public Venta realizarVentaConCupon(Venta venta, String codigoCupon) {
        venta.setCodigoCuponAplicado(codigoCupon);
        return realizarVenta(venta);
    }

    /**
     * Calcula el total de una venta con un cupón sin procesarla
     */
    public double calcularTotalConCupon(int idCliente, String codigoCupon) {
        Carrito carrito = carritoService.getCarritoByIdCliente(idCliente);
        
        if (carrito == null || carrito.getItems() == null || carrito.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío o no existe.");
        }
        
        double totalBase = 0;
        
        // Calcular total base
        for (ItemCarrito itemCarrito : carrito.getItems()) {
            Producto producto = productoService.listarProductoPorId(itemCarrito.getIdProducto());
            if (producto == null) {
                throw new IllegalArgumentException("Producto con ID " + itemCarrito.getIdProducto() + " no encontrado.");
            }
            totalBase += itemCarrito.getCantidad() * producto.getPrecio();
        }
        
        // Aplicar cupón si existe
        if (codigoCupon != null && !codigoCupon.trim().isEmpty()) {
            if (!cuponDescuentoService.validarCupon(codigoCupon)) {
                throw new IllegalArgumentException("Cupón inválido o expirado: " + codigoCupon);
            }
            double descuento = cuponDescuentoService.calcularDescuento(codigoCupon, totalBase);
            return totalBase - descuento;
        }
        
        return totalBase;
    }

}
