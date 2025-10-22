package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.Comentario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    public List<Comentario> listarComentariosPorTicket(String ticketId) {
        throw new UnsupportedOperationException("Implementar lectura de comentarios desde CSV");
    }

    public Comentario agregarComentario(String ticketId, Comentario comentario) {
        throw new UnsupportedOperationException("Implementar almacenamiento de comentarios en CSV");
    }
}
