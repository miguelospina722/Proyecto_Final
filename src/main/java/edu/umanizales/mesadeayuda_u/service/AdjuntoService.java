package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.Adjunto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdjuntoService {

    public List<Adjunto> listarAdjuntosPorTicket(String ticketId) {
        throw new UnsupportedOperationException("Implementar lectura de adjuntos desde CSV");
    }

    public Adjunto agregarAdjunto(String ticketId, Adjunto adjunto) {
        throw new UnsupportedOperationException("Implementar almacenamiento de adjuntos en CSV");
    }
}
