package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public List<User> listUsers() {
        throw new UnsupportedOperationException("Implement CSV-backed user listing");
    }

    public User createUser(User user) {
        throw new UnsupportedOperationException("Implement CSV-backed user creation");
    }

    public User updateUser(String id, User user) {
        throw new UnsupportedOperationException("Implement CSV-backed user update");
    }

    public void deleteUser(String id) {
        throw new UnsupportedOperationException("Implement CSV-backed user deletion");
    }
}
