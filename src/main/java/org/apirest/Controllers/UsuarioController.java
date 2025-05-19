package org.apirest.Controllers;

import io.javalin.http.Context;
import io.javalin.http.BadRequestResponse;
import org.apirest.modelo.Mensaje;
import org.apirest.modelo.Usuario;
import org.apirest.service.UsuarioService;

import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void addUsuarioRoutes() {
        path("/api/usuarios", () -> {
            get(this::getAll);
            post(this::create);
            path("{id}", () -> {
                get(this::getById);
                put(this::update);
                delete(this::deleteById);
            });
        });
    }

    private void getAll(Context ctx) {
        try {
            List<Usuario> usuarios = usuarioService.getUsuarios();
            if (usuarios.isEmpty()) {
                ctx.status(404).json(new Mensaje("No hay usuarios registrados", null));
            } else {
                ctx.status(200).json(new Mensaje("Usuarios listados correctamente", usuarios));
            }
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al listar usuarios: " + e.getMessage(), null));
        }
    }

    private void getById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = usuarioService.getUsuario(id);
            if (usuario == null) {
                ctx.status(404).json(new Mensaje("Usuario no encontrado", null));
            } else {
                ctx.status(200).json(new Mensaje("Usuario encontrado", usuario));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al listar usuario: " + e.getMessage(), null));
        }
    }

    private void create(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            Usuario nuevoUsuario = usuarioService.createUsuario(usuario);
            ctx.status(201).json(new Mensaje("Usuario creado correctamente", nuevoUsuario));
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al crear usuario: " + e.getMessage(), null));
        }
    }

    private void update(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuarioActualizar = ctx.bodyAsClass(Usuario.class);
            
            if (usuarioService.getUsuario(id) == null) {
                ctx.status(404).json(new Mensaje("Usuario no encontrado con ID: " + id + " para actualizar", null));
                return;
            }
            usuarioActualizar.setIdUsuario(id);
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuarioActualizar);
            ctx.status(200).json(new Mensaje("Usuario actualizado correctamente", usuarioActualizado));
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Mensaje("Solicitud incorrecta: el formato de los datos es inválido.", null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al actualizar usuario: " + e.getMessage(), null));
        }
    }

    private void deleteById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            if (usuarioService.getUsuario(id) == null) {
                ctx.status(404).json(new Mensaje("Usuario no encontrado con ID: " + id + " para eliminar", null));
                return;
            }
            boolean eliminado = usuarioService.eliminarUsuario(id);
            if (eliminado) {
                ctx.status(200).json(new Mensaje("Usuario eliminado correctamente", true));
            } else {
                ctx.status(500).json(new Mensaje("Error al eliminar el usuario con ID: " + id, false));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido: " + ctx.pathParam("id"), null));
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error al eliminar usuario: " + e.getMessage(), null));
        }
    }
}
