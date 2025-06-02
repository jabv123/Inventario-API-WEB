package org.apirest.service;

import java.util.List;

import org.apirest.modelo.Usuario;
import org.apirest.repository.UsuarioRepo;

public class UsuarioService {

    private final UsuarioRepo usuarioRepo;
    private final LogSistemaService logSistemaService;

    public UsuarioService(UsuarioRepo usuarioRepo, LogSistemaService logSistemaService) {
        this.usuarioRepo = usuarioRepo;
        this.logSistemaService = logSistemaService;
    }

    //Listar todos los usuarios
    public List<Usuario> getUsuarios() {
        return usuarioRepo.getUsuarios();
    }

    //Listar usuario por id
    public Usuario getUsuario(int id) {
        return usuarioRepo.getUsuario(id);
    }

    //Crear usuario
    public Usuario createUsuario(Usuario usuario) {
        Usuario usuarioGuardado = usuarioRepo.createUsuario(usuario);
        logSistemaService.registrarLog("Crear usuario", usuario.getIdUsuario());
        return usuarioGuardado;
    }
    //Actualizar usuario
    public Usuario actualizarUsuario(Usuario usuarioActualizar) {
        logSistemaService.registrarLog("Actualizar usuario", usuarioActualizar.getIdUsuario());
        return usuarioRepo.actualizarUsuario(usuarioActualizar);
    }
    //Eliminar usuario
    public boolean eliminarUsuario(int idUsuario) {
        logSistemaService.registrarLog("Eliminar usuario", idUsuario);
        return usuarioRepo.eliminarUsuario(idUsuario);
    }

}
