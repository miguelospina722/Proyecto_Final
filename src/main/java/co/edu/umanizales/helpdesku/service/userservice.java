package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.user;

@Service
public class userservice extends csvbaseservice<user> {

    public userservice() {
        super("users.csv");
    }

    @Override
    protected user createEmpty() {
        return new user();
    }

    @Override
    protected String[] headerRow() {
        user sample = new user();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<user> list() {
        return findAll();
    }

    public user getById(String id) {
        return findById(id);
    }

    public user requireActiveUserById(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Este usuario no está activo.");
        }
        user stored = getById(userId);
        if (stored == null || !stored.isActive()) {
            throw new BadRequestException("Este usuario no está activo.");
        }
        return stored;
    }

    public user saveUser(user entity) {
        if (entity == null) {
            throw new BadRequestException("El usuario es obligatorio");
        }
        if (entity.getUsername() == null) {
            throw new BadRequestException("El username es obligatorio");
        }
        entity.setUsername(entity.getUsername());
        validateRole(entity);
        validateUniqueUsername(entity);
        return save(entity);
    }

    public boolean deleteUser(String id) {
        return delete(id);
    }

    private void validateRole(user entity) {
        if (entity.getRole() == null) {
            throw new BadRequestException("Asigna un rol correcto");
        }
    }

    private void validateUniqueUsername(user entity) {
        String username = entity.getUsername();
        List<user> current = findAll();
        for (int index = 0; index < current.size(); index++) {
            user existing = current.get(index);
            if (existing.getUsername() == null) {
                continue;
            }
            boolean sameId = entity.getId() != null && entity.getId().equals(existing.getId());
            if (!sameId && existing.getUsername().equalsIgnoreCase(username)) {
                throw new BadRequestException("Este usuario ya está creado");
            }
        }
    }
}
