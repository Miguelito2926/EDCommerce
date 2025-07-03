package com.ednaldo.edcommerce.services;

import com.ednaldo.edcommerce.dto.CategoryDTO;
import com.ednaldo.edcommerce.entities.Category;
import com.ednaldo.edcommerce.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public List<CategoryDTO> getCategories() {
        List<Category> all = categoryRepository.findAll();
        return all.stream().map(CategoryDTO::new).toList();
    }
}
