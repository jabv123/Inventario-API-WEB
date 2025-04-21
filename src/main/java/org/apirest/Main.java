package org.apirest;

import org.apirest.Controllers.CategoriaController;
import org.apirest.Controllers.ClienteController;
import org.apirest.Controllers.ProductoController;
import org.apirest.Controllers.ProveedorController;
import org.apirest.repository.ClienteRepo;
import org.apirest.repository.Impl.CategoriaRepoImpl;
import org.apirest.service.ClienteService;
import org.apirest.service.Impl.CategoriaServiceImpl;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        // Dependencias
        ClienteRepo clienteRepository = new ClienteRepo();
        ClienteService clienteService = new ClienteService(clienteRepository);
        CategoriaRepoImpl categoriaRepo = new CategoriaRepoImpl();
        CategoriaServiceImpl categoriaService = new CategoriaServiceImpl(categoriaRepo);

        // Configuración del servidor Spark
        port(8080);

        // Inicializar los controladores y definir las rutas
        new ProveedorController(); // Asumo que el constructor del controlador define las rutas
        new ProductoController();  // Asumo que el constructor del controlador define las rutas

        // Controlador de Clientes y sus rutas
        path("/api/clientes", () -> {
            new ClienteController(clienteService); // Asumo que el constructor define las rutas
        });

        // Controlador de Categorías y sus rutas
        path("/api/categorias", () -> {
            new CategoriaController(categoriaService); // Asumo que el constructor define las rutas
        });

        System.out.println("Servidor Spark iniciado y escuchando en el puerto 8080");
    }
}