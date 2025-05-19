package org.apirest;

import org.apirest.Controllers.CategoriaController;
import org.apirest.Controllers.ClienteController;
import org.apirest.Controllers.ImgProductoController;
import org.apirest.Controllers.ProductoController;
import org.apirest.Controllers.ProveedorController;
import org.apirest.Controllers.UsuarioController;
import org.apirest.repository.ClienteRepo;
import org.apirest.repository.ImgProductoRepo;
import org.apirest.repository.UsuarioRepo;
import org.apirest.service.ClienteService;
import org.apirest.service.ImgProductoService;
import org.apirest.service.UsuarioService;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        //Dependencias
        ClienteRepo clienteRepository = new ClienteRepo();
        ClienteService clienteService = new ClienteService(clienteRepository);

        UsuarioRepo usuarioRepository = new UsuarioRepo();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);

        ImgProductoRepo imgProductoRepository = new ImgProductoRepo();
        ImgProductoService imgProductoService = new ImgProductoService(imgProductoRepository);

        // Controladores
        ProductoController productoController = new ProductoController();
        CategoriaController categoriaController = new CategoriaController();
        ProveedorController proveedorController = new ProveedorController(); 
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