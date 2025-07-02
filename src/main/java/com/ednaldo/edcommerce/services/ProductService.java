package com.ednaldo.edcommerce.services;

import com.ednaldo.edcommerce.dto.CategoryDTO;
import com.ednaldo.edcommerce.dto.ProductDTO;
import com.ednaldo.edcommerce.dto.ProductMinDTO;
import com.ednaldo.edcommerce.entities.Category;
import com.ednaldo.edcommerce.entities.Product;
import com.ednaldo.edcommerce.repositories.ProductRepository;
import com.ednaldo.edcommerce.services.exceptions.DatabaseException;
import com.ednaldo.edcommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Page<ProductMinDTO> getProducts(String name, Pageable pageable) {
        Page<Product> all = productRepository.searchByName(name, pageable);
        return all.map(ProductMinDTO::new);
    }

    @Transactional
    public ProductDTO getProductById(Long id) throws ResourceNotFoundException {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found Id: " + id));

        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO insertProduct(ProductDTO request) {
        Product product = new Product();
        copyDtoToEntity(request, product);
        productRepository.save(product);
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional
    public void deleteProduct(Long id) throws ResourceNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource Not Found Id: " + id);
        }

        try {
            productRepository.deleteById(id);

        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial:");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.getCategories().clear();

        for (CategoryDTO catDto : dto.getCategories()) {
            Category cat = new Category();
            cat.setId(catDto.getId());
            entity.getCategories().add(cat);
        }
    }
}
