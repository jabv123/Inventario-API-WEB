package org.apirest.config;

import org.apirest.repository.ProductoRepo;
import org.apirest.repository.ProveedorRepo;
import org.apirest.service.*;
import org.apirest.repository.CarritoRepo;
import org.apirest.repository.CategoriaRepo;
import org.apirest.repository.ClienteRepo;
import org.apirest.repository.DetalleVentaRepo;
import org.apirest.repository.UsuarioRepo;
import org.apirest.repository.VentaRepo;
import org.apirest.repository.ImgProductoRepo;
import org.apirest.repository.ItemCarritoRepo;
import org.apirest.repository.LogSistemaRepo;
import org.apirest.repository.MetodoPagoRepo;
import org.apirest.service.ImgProductoService;
import org.apirest.service.LogSistemaService;
import org.apirest.service.MetodoPagoService;
import org.apirest.repository.CuponDescuentoRepo;
import org.apirest.repository.DetalleMetodoPagoRepo;
import org.apirest.service.CuponDescuentoService;
import org.apirest.repository.AjusteStockRepo;
import org.apirest.service.AjusteStockService;

import org.apirest.repository.EnvioSimuladoRepository;
import org.apirest.repository.EstadoEnvioRepository;
import org.apirest.service.EnvioSimuladoService;



public class AppDependencies {

    // Para productos
    private final ProductoRepo productoRepository;
    private final ProductoService productoService;

    // Para categorías
    private final CategoriaRepo categoriaRepository;
    private final CategoriaService categoriaService;

    // Para proveedores
    private final ProveedorRepo proveedorRepository;
    private final ProveedorService proveedorService;

    // Para clientes
    private final ClienteRepo clienteRepository;
    private final ClienteService clienteService;

    // Para usuarios
    private final UsuarioRepo usuarioRepository;
    private final UsuarioService usuarioService;

    // Para imágenes de productos
    private final ImgProductoRepo imgProductoRepository;
    private final ImgProductoService imgProductoService;

    // Para carritos
    private final CarritoRepo carritoRepository;
    private final ItemCarritoRepo itemCarritoRepository;
    private final CarritoService carritoService;

    // Para ventas
    private final VentaRepo ventaRepository;
    private final DetalleVentaRepo detalleVentaRepository;
    private final VentaService ventaService;

    // Para ajustes de stock
    private final AjusteStockRepo ajusteStockRepository;
    private final AjusteStockService ajusteStockService;

  // Para cupones de descuento
    private final CuponDescuentoRepo cuponDescuentoRepository;
    private final CuponDescuentoService cuponDescuentoService;

    // Para métodos de pago
    private final MetodoPagoRepo metodoPagoRepository;
    private final DetalleMetodoPagoRepo detalleMetodoPagoRepository;
    private final MetodoPagoService metodoPagoService;

    // Para logs del sistema
    private final LogSistemaRepo logSistemaRepository;
    private final LogSistemaService logSistemaService;
    
    // Para envío simulado
    private final EnvioSimuladoRepository envioSimuladoRepository;
    private final EnvioSimuladoService envioSimuladoService;

    // Para estado de envío
    private final EstadoEnvioRepository estadoEnvioRepository;
    private final EstadoEnvioService estadoEnvioService;

    // Constructor sin parámetros para que esta clase se encargue de inicializar todo
    public AppDependencies() {
        // Inicializar el servicio de logs del sistema
        logSistemaRepository = new LogSistemaRepo();
        logSistemaService = new LogSistemaService(logSistemaRepository);
        
        // Productos
        productoRepository = new ProductoRepo();
        productoService = new ProductoService(productoRepository);
        
        // Categorías
        categoriaRepository = new CategoriaRepo();
        categoriaService = new CategoriaService(categoriaRepository);
        
        // Proveedores
        proveedorRepository = new ProveedorRepo();
        proveedorService = new ProveedorService(proveedorRepository);
        
        // Clientes
        clienteRepository = new ClienteRepo();
        clienteService = new ClienteService(clienteRepository);
        
        // Usuarios
        usuarioRepository = new UsuarioRepo();
        usuarioService = new UsuarioService(usuarioRepository, logSistemaService);
        
        // Imágenes de Productos
        imgProductoRepository = new ImgProductoRepo();

        imgProductoService = new ImgProductoService(imgProductoRepository);        

        // Carritos
        carritoRepository = new CarritoRepo();
        itemCarritoRepository = new ItemCarritoRepo();
        carritoService = new CarritoService(carritoRepository, itemCarritoRepository, productoService);

        // Cupones de descuento
        cuponDescuentoRepository = new CuponDescuentoRepo();
        cuponDescuentoService = new CuponDescuentoService(cuponDescuentoRepository);

        // Metodos de pago
        metodoPagoRepository = new MetodoPagoRepo();
        detalleMetodoPagoRepository = new DetalleMetodoPagoRepo();
        metodoPagoService = new MetodoPagoService(metodoPagoRepository,  detalleMetodoPagoRepository, clienteService);

        // Ventas
        ventaRepository = new VentaRepo();
        detalleVentaRepository = new DetalleVentaRepo();
        ventaService = new VentaService(ventaRepository, detalleVentaRepository, carritoService, productoService, cuponDescuentoService, metodoPagoService);

        // Ajustes de stock
        ajusteStockRepository = new AjusteStockRepo();
        ajusteStockService = new AjusteStockService(ajusteStockRepository, productoService);

        // Envio
        envioSimuladoRepository = new EnvioSimuladoRepository();
        envioSimuladoService = new EnvioSimuladoService(envioSimuladoRepository);

        // Estado de envío
        estadoEnvioRepository = new EstadoEnvioRepository();
        estadoEnvioService = new EstadoEnvioService(estadoEnvioRepository);
    }

    // Getters para los servicios
    public ProductoService getProductoService() {
        return productoService;
    }

    public CategoriaService getCategoriaService() {
        return categoriaService;
    }

    public ProveedorService getProveedorService() {
        return proveedorService;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public ImgProductoService getImgProductoService() {
        return imgProductoService;
    }

    public CarritoService getCarritoService() {
        return carritoService;
    }    
    

    public VentaService getVentaService() {
        return ventaService;
    }

    public CuponDescuentoService getCuponDescuentoService() {
        return cuponDescuentoService;
    }

    public AjusteStockService getAjusteStockService() {
        return ajusteStockService;
    }

    public MetodoPagoService getMetodoPagoService() {
        return metodoPagoService;
    }

    public LogSistemaService getLogSistemaService() {
        return logSistemaService;
    }

    // Getter para el servicio de envío simulado
    public EnvioSimuladoService getEnvioSimuladoService() {
        return envioSimuladoService;
    }

    // Getter para el servicio de estado de envío -- Corrección
    public EstadoEnvioService getEstadoEnvioService(){
        return estadoEnvioService;
    }
}
