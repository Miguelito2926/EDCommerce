package com.ednaldo.edcommerce.services;

import com.ednaldo.edcommerce.dto.ProductDTO;
import com.ednaldo.edcommerce.entities.Product;
import com.ednaldo.edcommerce.repositories.ProductRepository;
import com.ednaldo.edcommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public ProductDTO getProductById(Long id) throws ResourceNotFoundException {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found Id: " + id));

        return new ProductDTO(product);
    }

    public ProductDTO insertProduct(ProductDTO request) {
        Product product = new Product();
        BeanUtils.copyProperties(request, product);
        productRepository.save(product);
        return new ProductDTO(product);
    }

    public ProductDTO updateProduct(ProductDTO productDTO, Long id) {

        Product prod = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found Id: " + id));

        prod.setName(productDTO.getName());
        prod.setDescription(productDTO.getDescription());
        prod.setPrice(productDTO.getPrice());
        prod.setImgUrl(productDTO.getImgUrl());

        Product productSave = productRepository.save(prod);
        BeanUtils.copyProperties(productSave, productDTO);
        return productDTO;
    }

    public void deleteProduct(Long id) throws ResourceNotFoundException {
        if (!productRepository.existsById(id)) {
            throw  new ResourceNotFoundException("Resource Not Found Id: " + id);
        }

        productRepository.deleteById(id);
    }
}
