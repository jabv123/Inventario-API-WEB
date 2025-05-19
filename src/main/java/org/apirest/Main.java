package org.apirest;

import org.apirest.Controllers.CategoriaController;
import org.apirest.Controllers.ClienteController;
import org.apirest.Controllers.ImgProductoController;
import org.apirest.Controllers.ProductoController;
import org.apirest.Controllers.ProveedorController;
import org.apirest.Controllers.UsuarioController;
import org.apirest.config.AppDependencies;
import org.apirest.service.*; // Importa todos los servicios necesarios

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

        // Controladores
        ProductoController productoController = new ProductoController(productoService);
        CategoriaController categoriaController = new CategoriaController(categoriaService);
        ProveedorController proveedorController = new ProveedorController(proveedorService);
        ImgProductoController imgProductoController = new ImgProductoController(imgProductoService);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        ClienteController clienteController = new ClienteController(clienteService);

        // Configuración del servidor Javalin
        Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                productoController.rutasProductos();
                categoriaController.rutasCategorias();
                proveedorController.rutasProveedores();
                imgProductoController.addImgProductoRoutes();
                usuarioController.addUsuarioRoutes();
                clienteController.addClienteRoutes();
                // Aquí añadir los controladores necesarios
                // ejemplo: OtroControladorJavalin().addSusRutas();
            });
            // Aquí se pueden añadir más configuraciones globales de Javalin si es necesario
            // config.showJavalinBanner = false; // Por ejemplo, para ocultar el banner de Javalin
        }).start(8080);

        System.out.println("=====Servidor Javalin iniciado y escuchando en el puerto 8080=====");

        // Mensajes de inicio
        System.out.println("Accede a la API en http://localhost:8080/");
    }
}