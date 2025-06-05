package com.ednaldo.edcommerce.services;

import com.ednaldo.edcommerce.dto.ProductDTO;
import com.ednaldo.edcommerce.entities.Product;
import com.ednaldo.edcommerce.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        Page<Product> all = productRepository.findAll(pageable);
        return all.map(ProductDTO::new);
    }

    public ProductDTO getProductById(Long id) throws Exception {
        var product = productRepository.findById(id)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatusCode.valueOf(404)));

        return new ProductDTO(product);
    }
}
