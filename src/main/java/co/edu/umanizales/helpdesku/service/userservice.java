package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public user saveUser(user entity) {
        if (entity == null) {
            throw new IllegalArgumentException("user entity must not be null");
        }
        String username = entity.getUsername();
        if (username == null) {
            throw new IllegalStateException("username must be defined before saving");
        }
        List<user> current = findAll();
        for (int index = 0; index < current.size(); index++) {
            user existing = current.get(index);
            if (username.equals(existing.getUsername())) {
                if (entity.getId() == null || !entity.getId().equals(existing.getId())) {
                    throw new IllegalStateException("username " + username + " already exists");
                }
            }
        }
        return save(entity);
    }

    public boolean deleteUser(String id) {
        return delete(id);
    }
}
