package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.Notificacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    public List<Notificacion> listarNotificacionesPorUsuario(String usuarioId) {
        throw new UnsupportedOperationException("Implementar listado de notificaciones desde CSV");
    }

    public Notificacion crearNotificacion(Notificacion notificacion) {
        throw new UnsupportedOperationException("Implementar creación de notificación en CSV");
    }

    public void marcarComoLeida(String notificacionId) {
        throw new UnsupportedOperationException("Implementar marcado de notificación como leída en CSV");
    }
}
