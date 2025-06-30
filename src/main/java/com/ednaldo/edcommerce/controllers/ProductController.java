package com.ednaldo.edcommerce.controllers;

import com.ednaldo.edcommerce.dto.ProductDTO;
import com.ednaldo.edcommerce.services.ProductService;
import com.ednaldo.edcommerce.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping
//    public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO request) {
//        ProductDTO productDTO = productService.insertProduct(request);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id")
//                .buildAndExpand(productDTO.getId()).toUri();
//        return ResponseEntity.created(uri).body(productDTO);
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insertProduct(@Valid @RequestBody ProductDTO request) {
        ProductDTO productDTO = productService.insertProduct(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @RequestParam(value = "name", defaultValue = "") String name,
            Pageable pageable) {
        return ResponseEntity.ok(productService.getProducts(name, pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.updateProduct(productDTO, id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
