package org.apirest;

import org.apirest.Controllers.CarritoController;
import org.apirest.Controllers.CategoriaController;
import org.apirest.Controllers.ClienteController;
import org.apirest.Controllers.ImgProductoController;
import org.apirest.Controllers.ProductoController;
import org.apirest.Controllers.ProveedorController;
import org.apirest.Controllers.UsuarioController;
import org.apirest.config.AppDependencies;
import org.apirest.service.*;
import org.apirest.Util.Mensaje;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;


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

        // Controladores
        ProductoController productoController = new ProductoController(productoService);
        CategoriaController categoriaController = new CategoriaController(categoriaService);
        ProveedorController proveedorController = new ProveedorController(proveedorService);
        ImgProductoController imgProductoController = new ImgProductoController(imgProductoService);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        ClienteController clienteController = new ClienteController(clienteService);
        CarritoController carritoController = new CarritoController(carritoService);

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
            });
            // Opcional: Deshabilitar el banner de Javalin en la consola
            // config.showJavalinBanner = false;
        });

        // MANEJO DE EXCEPCIONES CENTRALIZADO
        // Indica que el recurso específico solicitado por el cliente no pudo ser encontrado en el servidor.
        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ctx.status(404).json(new Mensaje(e.getMessage() != null ? e.getMessage() : "Recurso no encontrado", null));
        });

        // Indica que la solicitud del cliente no se pudo procesar debido a un error de sintaxis o formato.
        app.exception(BadRequestResponse.class, (e, ctx) -> {
            ctx.status(400).json(new Mensaje(e.getMessage() != null ? e.getMessage() : "Petición incorrecta", null));
        });

        // Excepción para manejar errores de JSON malformado no se puedo serializar o deserializar
        app.exception(JsonProcessingException.class, (e, ctx) -> { // Específico para Jackson
            ctx.status(400).json(new Mensaje("JSON inválido o malformado: " + e.getOriginalMessage(), null));
        });

        //Indica algun error al desarrollador o error de negocio
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(400).json(new Mensaje("Argumento ilegal o datos de entrada inválidos: " + e.getMessage(), null));
        });
        
        // Manejador genérico para cualquier otra excepción no capturada antes
        app.exception(Exception.class, (e, ctx) -> {
            // Loguear el error
            System.err.println("Error no manejado capturado: " + e.getMessage());
            e.printStackTrace(); // Para desarrollo, considera un logger más robusto para producción
            ctx.status(500).json(new Mensaje("Error interno del servidor. Por favor, intente más tarde.", null));
        });

        app.start(8080);

        System.out.println("=====Servidor Javalin iniciado y escuchando en el puerto 8080=====");
        System.out.println("Accede a la API en http://localhost:8080/");
    }
}