package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.comment;

@Service
public class commentservice extends csvbaseservice<comment> {

    public commentservice() {
        super("comments.csv");
    }

    @Override
    protected comment createEmpty() {
        return new comment();
    }

    @Override
    protected String[] headerRow() {
        comment sample = new comment();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<comment> list() {
        return findAll();
    }

    public comment getById(String id) {
        return findById(id);
    }

    public comment saveComment(comment entity) {
        return save(entity);
    }

    public boolean deleteComment(String id) {
        return delete(id);
    }
}
