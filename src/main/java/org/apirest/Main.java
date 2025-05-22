package org.apirest;

import org.apirest.Controllers.CarritoController;
import org.apirest.Controllers.CategoriaController;
import org.apirest.Controllers.ClienteController;
import org.apirest.Controllers.ImgProductoController;
import org.apirest.Controllers.ProductoController;
import org.apirest.Controllers.ProveedorController;
import org.apirest.Controllers.UsuarioController;
import org.apirest.Controllers.VentaController;
import org.apirest.config.AppDependencies;
import org.apirest.config.ExceptionsConfig;
import org.apirest.service.*;
import io.javalin.Javalin;


public class Main {
    public static void main(String[] args) {
        // Configuración centralizada de dependencias
        AppDependencies appConfig = new AppDependencies();

        // Obtener servicios desde AppConfig
        ProductoService productoService = appConfig.getProductoService();
        CategoriaService categoriaService = appConfig.getCategoriaService();
        ProveedorService proveedorService = appConfig.getProveedorService();
        ClienteService clienteService = appConfig.getClienteService();
        UsuarioService usuarioService = appConfig.getUsuarioService();
        ImgProductoService imgProductoService = appConfig.getImgProductoService();
        CarritoService carritoService = appConfig.getCarritoService();
        VentaService ventaService = appConfig.getVentaService();

        // Controladores
        ProductoController productoController = new ProductoController(productoService);
        CategoriaController categoriaController = new CategoriaController(categoriaService);
        ProveedorController proveedorController = new ProveedorController(proveedorService);
        ImgProductoController imgProductoController = new ImgProductoController(imgProductoService);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        ClienteController clienteController = new ClienteController(clienteService);
        CarritoController carritoController = new CarritoController(carritoService);
        VentaController ventaController = new VentaController(ventaService);

        // Configuración del servidor Javalin
        Javalin app = Javalin.create(config -> {
            // Configuración de rutas
            config.router.apiBuilder(() -> {
                productoController.rutasProductos();
                categoriaController.rutasCategorias();
                proveedorController.rutasProveedores();
                imgProductoController.addImgProductoRoutes();
                usuarioController.addUsuarioRoutes();
                clienteController.addClienteRoutes();
                carritoController.rutasCarrito();
                ventaController.rutasVentas();
            });
            // Opcional: Deshabilitar el banner de Javalin en la consola
            // config.showJavalinBanner = false;
        });

        // Manejo de excepciones
        ExceptionsConfig.registrarExcepciones(app);

        app.start(8080);

        System.out.println("=====Servidor Javalin iniciado y escuchando en el puerto 8080=====");
        System.out.println("Accede a la API en http://localhost:8080/");
    }
}