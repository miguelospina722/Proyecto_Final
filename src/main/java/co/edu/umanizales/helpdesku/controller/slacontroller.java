package co.edu.umanizales.helpdesku.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.umanizales.helpdesku.model.sla;
import co.edu.umanizales.helpdesku.service.slaservice;

@RestController
@RequestMapping("/api/slas")
public class slacontroller {

    private final slaservice slaService;

    public slacontroller(slaservice slaService) {
        this.slaService = slaService;
    }

    @GetMapping
    public ResponseEntity<List<sla>> getAll() {
        return ResponseEntity.ok(slaService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<sla> getById(@PathVariable String id) {
        sla found = slaService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<sla> create(@RequestBody sla request) {
        request.setId(null);
        sla saved = slaService.saveSla(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<sla> update(@PathVariable String id, @RequestBody sla request) {
        sla existing = slaService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        return ResponseEntity.ok(slaService.saveSla(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = slaService.deleteSla(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
