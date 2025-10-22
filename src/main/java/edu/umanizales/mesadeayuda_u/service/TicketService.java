package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.Adjunto;
import edu.umanizales.mesadeayuda_u.model.Comentario;
import edu.umanizales.mesadeayuda_u.model.EstadoTicket;
import edu.umanizales.mesadeayuda_u.model.Prioridad;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    public void cambiarEstado(String ticketId, EstadoTicket nuevoEstado) {
        throw new UnsupportedOperationException("Implementar cambio de estado usando almacenamiento CSV");
    }

    public void cambiarPrioridad(String ticketId, Prioridad nuevaPrioridad) {
        throw new UnsupportedOperationException("Implementar cambio de prioridad usando almacenamiento CSV");
    }

    public void reasignarTicket(String ticketId, String nuevoAsignadoId) {
        throw new UnsupportedOperationException("Implementar reasignación usando almacenamiento CSV");
    }

    public void agregarComentario(String ticketId, Comentario comentario) {
        throw new UnsupportedOperationException("Implementar adición de comentarios usando almacenamiento CSV");
    }

    public void adjuntarArchivo(String ticketId, Adjunto adjunto) {
        throw new UnsupportedOperationException("Implementar adjuntos usando almacenamiento CSV");
    }

    public LocalDateTime calcularVencimientoPorSla(String ticketId) {
        throw new UnsupportedOperationException("Implementar cálculo de vencimiento SLA usando políticas configuradas");
    }
}
