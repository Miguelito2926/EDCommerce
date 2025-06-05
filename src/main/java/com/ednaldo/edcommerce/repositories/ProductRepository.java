package com.ednaldo.edcommerce.repositories;

import com.ednaldo.edcommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
