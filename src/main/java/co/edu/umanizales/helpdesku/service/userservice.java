package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.exception.BadRequestException;
import co.edu.umanizales.helpdesku.model.User;

@Service
public class UserService extends CsvBaseService<User> {

    public UserService() {
        super("users.csv");
    }

    @Override
    protected User createEmpty() {
        return new User();
    }

    @Override
    protected String[] headerRow() {
        User sample = new User();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<User> list() {
        return findAll();
    }

    public User getById(String id) {
        return findById(id);
    }

    public User requireActiveUserById(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new BadRequestException("Este usuario no está activo.");
        }
        User stored = getById(userId);
        if (stored == null || !stored.isActive()) {
            throw new BadRequestException("Este usuario no está activo.");
        }
        return stored;
    }

    public User saveUser(User entity) {
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

    private void validateRole(User entity) {
        if (entity.getRole() == null) {
            throw new BadRequestException("Asigna un rol correcto");
        }
    }

    private void validateUniqueUsername(User entity) {
        String username = entity.getUsername();
        List<User> current = findAll();
        for (int index = 0; index < current.size(); index++) {
            User existing = current.get(index);
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
