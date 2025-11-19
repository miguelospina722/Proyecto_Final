package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.category;

@Service
public class categoryservice extends csvbaseservice<category> {

    public categoryservice() {
        super("categories.csv");
    }

    @Override
    protected category createEmpty() {
        return new category();
    }

    @Override
    protected String[] headerRow() {
        category sample = new category();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<category> list() {
        return findAll();
    }

    public category getById(String id) {
        return findById(id);
    }

    public category saveCategory(category entity) {
        return save(entity);
    }

    public boolean deleteCategory(String id) {
        return delete(id);
    }
}
