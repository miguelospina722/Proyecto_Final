package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.assignment;

@Service
public class assignmentservice extends csvbaseservice<assignment> {

    public assignmentservice() {
        super("assignments.csv");
    }

    @Override
    protected assignment createEmpty() {
        return new assignment();
    }

    @Override
    protected String[] headerRow() {
        assignment sample = new assignment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<assignment> list() {
        return findAll();
    }

    public assignment getById(String id) {
        return findById(id);
    }

    public assignment saveAssignment(assignment entity) {
        return save(entity);
    }

    public boolean deleteAssignment(String id) {
        return delete(id);
    }
}
