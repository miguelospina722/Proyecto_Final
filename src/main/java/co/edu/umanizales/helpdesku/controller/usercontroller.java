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

import co.edu.umanizales.helpdesku.model.user;
import co.edu.umanizales.helpdesku.service.userservice;

@RestController
@RequestMapping("/api/users")
public class usercontroller {

    private final userservice userService;

    public usercontroller(userservice userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<user>> getAll() {
        List<user> users = userService.list();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<user> getById(@PathVariable String id) {
        user found = userService.getById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping
    public ResponseEntity<user> create(@RequestBody user request) {
        request.setId(null);
        user saved = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<user> update(@PathVariable String id, @RequestBody user request) {
        user existing = userService.getById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        request.setId(id);
        request.setCreatedAt(existing.getCreatedAt());
        user updated = userService.saveUser(request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean removed = userService.deleteUser(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
