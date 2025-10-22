package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    public List<Categoria> listarCategorias() {
        throw new UnsupportedOperationException("Implementar listado de categorías desde CSV");
    }

    public Categoria crearCategoria(Categoria categoria) {
        throw new UnsupportedOperationException("Implementar creación de categoría en CSV");
    }

    public Categoria actualizarCategoria(String id, Categoria categoria) {
        throw new UnsupportedOperationException("Implementar actualización de categoría en CSV");
    }

    public void eliminarCategoria(String id) {
        throw new UnsupportedOperationException("Implementar eliminación de categoría en CSV");
    }
}
