package edu.umanizales.mesadeayuda_u.controller;

import edu.umanizales.mesadeayuda_u.model.Notificacion;
import edu.umanizales.mesadeayuda_u.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> listarPorUsuario(@PathVariable("usuarioId") String usuarioId) {
        return ResponseEntity.ok(notificacionService.listarNotificacionesPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Notificacion> crear(@RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(notificacionService.crearNotificacion(notificacion));
    }

    @PostMapping("/{id}/leida")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable("id") String notificacionId) {
        notificacionService.marcarComoLeida(notificacionId);
        return ResponseEntity.accepted().build();
    }
}
