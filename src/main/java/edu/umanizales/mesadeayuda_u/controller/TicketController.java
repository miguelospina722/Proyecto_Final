package edu.umanizales.mesadeayuda_u.controller;

import edu.umanizales.mesadeayuda_u.model.Adjunto;
import edu.umanizales.mesadeayuda_u.model.Comentario;
import edu.umanizales.mesadeayuda_u.model.EstadoTicket;
import edu.umanizales.mesadeayuda_u.model.Prioridad;
import edu.umanizales.mesadeayuda_u.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable("id") String ticketId,
                                              @RequestBody CambiarEstadoRequest request) {
        ticketService.cambiarEstado(ticketId, request.nuevoEstado());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/prioridad")
    public ResponseEntity<Void> cambiarPrioridad(@PathVariable("id") String ticketId,
                                                 @RequestBody CambiarPrioridadRequest request) {
        ticketService.cambiarPrioridad(ticketId, request.nuevaPrioridad());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/reasignar")
    public ResponseEntity<Void> reasignar(@PathVariable("id") String ticketId,
                                           @RequestBody ReasignarTicketRequest request) {
        ticketService.reasignarTicket(ticketId, request.nuevoAsignadoId());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<Void> agregarComentario(@PathVariable("id") String ticketId,
                                                  @RequestBody Comentario comentario) {
        ticketService.agregarComentario(ticketId, comentario);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/adjuntos")
    public ResponseEntity<Void> adjuntarArchivo(@PathVariable("id") String ticketId,
                                                @RequestBody Adjunto adjunto) {
        ticketService.adjuntarArchivo(ticketId, adjunto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{id}/sla")
    public ResponseEntity<LocalDateTime> calcularVencimiento(@PathVariable("id") String ticketId) {
        LocalDateTime vencimiento = ticketService.calcularVencimientoPorSla(ticketId);
        return ResponseEntity.ok(vencimiento);
    }

    public record CambiarEstadoRequest(EstadoTicket nuevoEstado) {
    }

    public record CambiarPrioridadRequest(Prioridad nuevaPrioridad) {
    }

    public record ReasignarTicketRequest(String nuevoAsignadoId) {
    }
}
