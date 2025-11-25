package co.edu.umanizales.helpdesku.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.umanizales.helpdesku.model.Category;

@Service
public class CategoryService extends CsvBaseService<Category> {

    public CategoryService() {
        super("categories.csv");
    }

    @Override
    protected Category createEmpty() {
        return new Category();
    }

    @Override
    protected String[] headerRow() {
        Category sample = new Category();
        String[] headers = sample.headers();
        String[] copy = new String[headers.length];
        for (int index = 0; index < headers.length; index++) {
            copy[index] = headers[index];
        }
        return copy;
    }

    public List<Category> list() {
        return findAll();
    }

    public Category getById(String id) {
        return findById(id);
    }

    public Category saveCategory(Category entity) {
        return save(entity);
    }

    public boolean deleteCategory(String id) {
        return delete(id);
    }
}
