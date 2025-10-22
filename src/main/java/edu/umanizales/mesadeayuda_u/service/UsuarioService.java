package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    public List<Usuario> listarUsuarios() {
        throw new UnsupportedOperationException("Implementar lectura de usuarios desde CSV");
    }

    public Usuario crearUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Implementar creación de usuario en CSV");
    }

    public Usuario actualizarUsuario(String id, Usuario usuario) {
        throw new UnsupportedOperationException("Implementar actualización de usuario en CSV");
    }

    public void eliminarUsuario(String id) {
        throw new UnsupportedOperationException("Implementar eliminación de usuario en CSV");
    }
}
