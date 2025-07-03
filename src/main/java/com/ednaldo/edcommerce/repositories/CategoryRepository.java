package com.ednaldo.edcommerce.repositories;

import com.ednaldo.edcommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
