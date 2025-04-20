package org.apirest;

import org.apirest.Controllers.ClienteController;
import org.apirest.Controllers.ProductoController;
import org.apirest.Controllers.ProveedorController;
import org.apirest.Controllers.UsuarioController;
import org.apirest.repository.ClienteRepo;
import org.apirest.repository.UsuarioRepo;
import org.apirest.service.ClienteService;
import org.apirest.service.UsuarioService;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        //Dependencias
        ClienteRepo clienteRepository = new ClienteRepo();
        ClienteService clienteService = new ClienteService(clienteRepository);

        UsuarioRepo usuarioRepository = new UsuarioRepo();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);

        // Configuración del servidor Spark
        port(8080);

        // Inicializar los controladores
        new ProveedorController();
        new ProductoController();

        new ClienteController(clienteService);

        new UsuarioController(usuarioService);
        

        System.out.println("Servidor Spark iniciado y escuchando en el puerto 8080");
    }
}