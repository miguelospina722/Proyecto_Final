package edu.umanizales.mesadeayuda_u.controller;

import edu.umanizales.mesadeayuda_u.model.Asignacion;
import edu.umanizales.mesadeayuda_u.service.AsignacionService;
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
@RequestMapping("/api/asignaciones")
@RequiredArgsConstructor
public class AsignacionController {

    private final AsignacionService asignacionService;

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<Asignacion>> listarPorTicket(@PathVariable("ticketId") String ticketId) {
        return ResponseEntity.ok(asignacionService.listarAsignacionesPorTicket(ticketId));
    }

    @PostMapping
    public ResponseEntity<Asignacion> registrar(@RequestBody Asignacion asignacion) {
        return ResponseEntity.ok(asignacionService.registrarAsignacion(asignacion));
    }
}
