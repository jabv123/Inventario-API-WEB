package org.apirest.service;

import java.util.List;

import org.apirest.modelo.Usuario;
import org.apirest.repository.UsuarioRepo;

public class UsuarioService {

    private final UsuarioRepo usuarioRepo;
    public UsuarioService(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
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
        return usuarioRepo.createUsuario(usuario);
    }
    //Actualizar usuario
    public Usuario actualizarUsuario(Usuario usuarioActualizar) {
        return usuarioRepo.actualizarUsuario(usuarioActualizar);
    }
    //Eliminar usuario
    public boolean eliminarUsuario(int idUsuario) {
        return usuarioRepo.eliminarUsuario(idUsuario);
    }

}
