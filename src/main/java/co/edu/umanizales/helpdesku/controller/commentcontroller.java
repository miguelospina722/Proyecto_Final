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

import co.edu.umanizales.helpdesku.model.comment;
import co.edu.umanizales.helpdesku.service.commentservice;

@RestController
@RequestMapping("/api/comments")
public class commentcontroller {

    private final commentservice commentService;

    public commentcontroller(commentservice commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<comment>> getAll() {
        return ResponseEntity.ok(commentService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<comment> getById(@PathVariable String id) {
        comment found = commentService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<comment> create(@RequestBody comment request) {
        request.setId(null);
        comment saved = commentService.saveComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<comment> update(@PathVariable String id, @RequestBody comment request) {
        comment existing = commentService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        return ResponseEntity.ok(commentService.saveComment(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = commentService.deleteComment(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
