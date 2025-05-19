package org.apirest.config;

import org.apirest.repository.ProductoRepo;
import org.apirest.repository.ProveedorRepo;
import org.apirest.service.ProductoService;
import org.apirest.service.ProveedorService;
import org.apirest.repository.CategoriaRepo;
import org.apirest.service.CategoriaService;
import org.apirest.repository.ClienteRepo;
import org.apirest.service.ClienteService;
import org.apirest.repository.UsuarioRepo;
import org.apirest.service.UsuarioService;
import org.apirest.repository.ImgProductoRepo;
import org.apirest.service.ImgProductoService;

public class AppDependencies {

    private final ProductoRepo productoRepository;
    private final ProductoService productoService;

    private final CategoriaRepo categoriaRepository;
    private final CategoriaService categoriaService;

    private final ProveedorRepo proveedorRepository;
    private final ProveedorService proveedorService;

    private final ClienteRepo clienteRepository;
    private final ClienteService clienteService;

    private final UsuarioRepo usuarioRepository;
    private final UsuarioService usuarioService;

    private final ImgProductoRepo imgProductoRepository;
    private final ImgProductoService imgProductoService;

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
}
