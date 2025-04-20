package org.apirest.Controllers;

import org.apirest.modelo.Mensaje;
import org.apirest.modelo.Usuario;
import org.apirest.service.UsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

import static spark.Spark.*;

import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        rutasUsuario();
    }

    private void rutasUsuario(){

        path("/api/usuarios", () -> {

            //Listar todos los usuarios
            get("", (req, res) -> {
                res.type("application/json");
                try{

                    List<Usuario> usuarios = usuarioService.getUsuarios();
                    if (usuarios.isEmpty()) {
                        res.status(404);
                        return "No hay usuarios registrados";
                    } else {
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Usuarios listados correctamente", usuarios));
                    }
                } catch (Exception e) {
                    res.status(500);
                    return "Error al listar usuarios: " + e.getMessage();
                }
            });

            //Listar usuario por id
            get("/:id", (req, res) -> {
                res.type("application/json");
                try{
                    int id = Integer.parseInt(req.params(":id"));
                    Usuario usuario = usuarioService.getUsuario(id);
                    if (usuario == null) {
                        res.status(404);
                        return "Usuario no encontrado";
                    } else {
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Usuario encontrado", usuario));
                    }
                } catch (Exception e) {
                    res.status(500);
                    return "Error al listar usuario: " + e.getMessage();
                }
            });

            //Crear usuario
            post("", (req, res) -> {
                res.type("application/json");
                try{
                    Usuario usuario = objectMapper.readValue(req.body(), Usuario.class);
                    Usuario nuevoUsuario = usuarioService.createUsuario(usuario);
                    res.status(201);
                    return objectMapper.writeValueAsString(new Mensaje("Usuario creado correctamente", nuevoUsuario));
                } catch (Exception e) {
                    res.status(500);
                    return "Error al crear usuario: " + e.getMessage();
                }
            });

            //Actualizar usuario
            put("/:id", (req, res) -> {
                res.type("application/json");
                try{
                    int id = Integer.parseInt(req.params(":id"));
                    Usuario usuarioActualizar = objectMapper.readValue(req.body(), Usuario.class);
                    usuarioActualizar.setIdUsuario(id);
                    Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuarioActualizar);
                    if (usuarioActualizado == null) {
                        res.status(404);
                        return "Usuario no encontrado";
                    } else {
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Usuario actualizado correctamente", usuarioActualizado));
                    }
                } catch (Exception e) {
                    res.status(500);
                    return "Error al actualizar usuario: " + e.getMessage();
                }
            });

            //Eliminar usuario
            delete("/:id", (req, res) -> {
                res.type("application/json");
                try{
                    int id = Integer.parseInt(req.params(":id"));
                    boolean eliminado = usuarioService.eliminarUsuario(id);
                    if (eliminado) {
                        res.status(200);
                        return objectMapper.writeValueAsString(new Mensaje("Usuario eliminado correctamente", eliminado));
                    } else {
                        res.status(404);
                        return "Usuario no encontrado";
                    }
                } catch (Exception e) {
                    res.status(500);
                    return "Error al eliminar usuario: " + e.getMessage();
                }
            });
        });
    }
}
