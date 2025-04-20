package org.apirest.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apirest.modelo.Usuario;

public class UsuarioRepo {

    private final List<Usuario> usuarios = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger id = new AtomicInteger(1);

    //Listar usuarios
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    //Listar usuario por id
    public Usuario getUsuario(int id) {
        return usuarios.stream().filter(u -> u.getIdUsuario() == id).findFirst().orElse(null);
    }

    //Crear usuario
    public Usuario createUsuario(Usuario usuario) {
        usuario.setIdUsuario(id.getAndIncrement());
        usuarios.add(usuario);
        return usuario;
    }

    //Actualizar usuario
    public Usuario actualizarUsuario(Usuario usuarioActualizar){
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getIdUsuario() == usuarioActualizar.getIdUsuario()) {
                usuarios.set(i, usuarioActualizar);
                return usuarioActualizar;
            }
        }
        return null;
    }

    //Eliminar usuario
    public boolean eliminarUsuario(int idUsuario){
        return usuarios.removeIf(u -> u.getIdUsuario() == idUsuario);
    }


}
