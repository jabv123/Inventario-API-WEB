package org.apirest.service;

import java.util.List;
import java.util.ArrayList;

import org.apirest.modelo.Venta;
import org.apirest.modelo.Carrito;
import org.apirest.modelo.DetalleVenta;
import org.apirest.modelo.ItemCarrito;
import org.apirest.modelo.MetodoPago;
import org.apirest.modelo.Producto;
import org.apirest.modelo.Factura;
import org.apirest.modelo.Cliente;
import org.apirest.repository.DetalleVentaRepo;
import org.apirest.repository.VentaRepo;

public class VentaService {

    private final VentaRepo ventaRepository;
    private final DetalleVentaRepo detalleVentaRepository;
    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final CuponDescuentoService cuponDescuentoService;
    private final MetodoPagoService metodoPagoService;
    private final FacturaService facturaService;
    private final ClienteService clienteService;

    public VentaService(VentaRepo ventaRepository, DetalleVentaRepo detalleVentaRepository, CarritoService carritoService, ProductoService productoService, CuponDescuentoService cuponDescuentoService, MetodoPagoService metodoPagoService, FacturaService facturaService, ClienteService clienteService) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.cuponDescuentoService = cuponDescuentoService;
        this.metodoPagoService = metodoPagoService;
        this.facturaService = facturaService;
        this.clienteService = clienteService;
    }

    public Venta realizarVenta(Venta venta) {
        /* *
         * Desde el controlador se obtiene el cliente.
         * Para obtener los detalles hacemos uso de servicio de carrito para obtenerlo 
         * Con esto completamos los detalles de la venta
         */

        // Obtener el cliente desde la venta
        int idCliente = venta.getIdCliente();
        
        // VALIDACIÓN OBLIGATORIA: La venta debe tener un método de pago
        if (venta.getIdMetodoPago() == 0) {
            throw new IllegalArgumentException("La venta debe especificar un método de pago válido");
        }
        
        // Validar que el método de pago pertenece al cliente y está activo
        MetodoPago metodoPagoValidado = metodoPagoService.obtenerMetodoPagoParaVenta(idCliente, venta.getIdMetodoPago());
        if (metodoPagoValidado == null) {
            throw new IllegalArgumentException("Método de pago no válido para este cliente o no activo");
        }
        
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
        }        carritoService.eliminarCarritoPorIdCliente(idCliente);
        // Actualizar el stock de los productos vendidos
        for (ItemCarrito itemCarrito : carrito.getItems()) {
            productoService.reducirStock(itemCarrito.getIdProducto(), itemCarrito.getCantidad());
        }

        // Crear factura automáticamente para la venta completada
        crearFacturaParaVenta(ventaGuardada);

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
     * Crea automáticamente una factura para la venta completada
     */
    private void crearFacturaParaVenta(Venta venta) {
        try {
            // Obtener datos del cliente
            Cliente cliente = clienteService.listarClientePorId(venta.getIdCliente());
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente no encontrado con ID: " + venta.getIdCliente());
            }

            // Formatear datos del cliente para la factura
            String datosCliente = formatearDatosCliente(cliente);

            // Crear factura
            Factura factura = new Factura();
            factura.setIdVenta(venta.getId());
            factura.setTotalFacturado(venta.getTotal());
            factura.setDatosClienteFactura(datosCliente);
            // numeroFactura y fechaFactura se generan automáticamente en FacturaService

            // Guardar factura
            facturaService.crearFactura(factura);
            
        } catch (Exception e) {
            // Log del error pero no interrumpir la venta
            System.err.println("Error al crear factura para venta ID " + venta.getId() + ": " + e.getMessage());
        }
    }

    /**
     * Formatea los datos del cliente para incluir en la factura
     */
    private String formatearDatosCliente(Cliente cliente) {
        StringBuilder datos = new StringBuilder();
        datos.append("ID Cliente: ").append(cliente.getIdCliente()).append(" | ");
        datos.append("Nombre: ").append(cliente.getNombre()).append(" | ");
        datos.append("Email: ").append(cliente.getEmail()).append(" | ");
        if (cliente.getDireccion() != null && !cliente.getDireccion().trim().isEmpty()) {
            datos.append("Dirección: ").append(cliente.getDireccion());
        } else {
            datos.append("Dirección: No especificada");
        }
        return datos.toString();
    }

}
