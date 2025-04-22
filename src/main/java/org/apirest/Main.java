package org.apirest;

import org.apirest.Controllers.CategoriaController;
import org.apirest.Controllers.ClienteController;
import org.apirest.Controllers.ProductoController;
import org.apirest.Controllers.ProveedorController;
import org.apirest.repository.ClienteRepo;
import org.apirest.service.ClienteService;


import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        // Dependencias
        ClienteRepo clienteRepository = new ClienteRepo();
        ClienteService clienteService = new ClienteService(clienteRepository);

        // Configuración del servidor Spark
        port(8080);

        // Inicializar los controladores y definir las rutas
        new ProveedorController();
        new ProductoController();
        new CategoriaController();


        System.out.println("Servidor Spark iniciado y escuchando en el puerto 8080");
    }
}