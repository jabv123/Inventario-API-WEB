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
import org.apirest.repository.CuponDescuentoRepo;
import org.apirest.repository.EnvioSimuladoRepository; // Importar el repositorio de envío
import org.apirest.service.EnvioSimuladoService; // Importar el servicio de envío


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

    // Para cupones de descuento
    private final CuponDescuentoRepo cuponDescuentoRepository;
    private final CuponDescuentoService cuponDescuentoService;

    // Para envío simulado
    private final EnvioSimuladoRepository envioSimuladoRepository; // Declaración
    private final EnvioSimuladoService envioSimuladoService;       // Declaración

    // Constructor sin parámetros para que esta clase se encargue de inicializar todo
    public AppDependencies() {
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
        usuarioService = new UsuarioService(usuarioRepository);

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

        // Ventas
        ventaRepository = new VentaRepo();
        detalleVentaRepository = new DetalleVentaRepo();
        ventaService = new VentaService(ventaRepository, detalleVentaRepository, carritoService, productoService, cuponDescuentoService);

        // Envio
        this.envioSimuladoRepository = new EnvioSimuladoRepository();
        this.envioSimuladoService = new EnvioSimuladoService(this.envioSimuladoRepository);
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

    // Getter para el servicio de envío simulado
    public EnvioSimuladoService getEnvioSimuladoService() {
        return envioSimuladoService;
    }
}
