package edu.umanizales.helpdesk_u.service;

import edu.umanizales.helpdesk_u.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    public List<Category> listCategories() {
        throw new UnsupportedOperationException("Implement CSV-backed category listing");
    }

    public Category createCategory(Category category) {
        throw new UnsupportedOperationException("Implement CSV-backed category creation");
    }

    public Category updateCategory(String id, Category category) {
        throw new UnsupportedOperationException("Implement CSV-backed category update");
    }

    public void deleteCategory(String id) {
        throw new UnsupportedOperationException("Implement CSV-backed category deletion");
    }
}
