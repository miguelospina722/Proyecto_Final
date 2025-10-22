package edu.umanizales.mesadeayuda_u.service;

import edu.umanizales.mesadeayuda_u.model.Asignacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionService {

    public List<Asignacion> listarAsignacionesPorTicket(String ticketId) {
        throw new UnsupportedOperationException("Implementar consulta de asignaciones desde CSV");
    }

    public Asignacion registrarAsignacion(Asignacion asignacion) {
        throw new UnsupportedOperationException("Implementar registro de asignaciones en CSV");
    }
}
