package com.ednaldo.edcommerce.services;

import com.ednaldo.edcommerce.dto.ProductDTO;
import com.ednaldo.edcommerce.entities.Product;
import com.ednaldo.edcommerce.repositories.ProductRepository;
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

    public List<ProductDTO> getProducts() {
        List<Product> all = productRepository.findAll();
        List<ProductDTO> dtos = all.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getImgUrl())).collect(Collectors.toList());
        return dtos;
    }

    public ProductDTO getProductById(Long id) throws Exception {
        var product = productRepository.findById(id)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatusCode.valueOf(404)));

        return new ProductDTO(product);
    }
}
