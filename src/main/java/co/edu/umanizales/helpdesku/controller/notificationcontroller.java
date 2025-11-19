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

import co.edu.umanizales.helpdesku.model.notification;
import co.edu.umanizales.helpdesku.service.notificationservice;

@RestController
@RequestMapping("/api/notifications")
public class notificationcontroller {

    private final notificationservice notificationService;

    public notificationcontroller(notificationservice notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<notification>> getAll() {
        return ResponseEntity.ok(notificationService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<notification> getById(@PathVariable String id) {
        notification found = notificationService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<notification> create(@RequestBody notification request) {
        request.setId(null);
        notification saved = notificationService.saveNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<notification> update(@PathVariable String id, @RequestBody notification request) {
        notification existing = notificationService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        return ResponseEntity.ok(notificationService.saveNotification(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = notificationService.deleteNotification(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
