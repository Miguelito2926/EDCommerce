package com.ednaldo.edcommerce.controllers;

import com.ednaldo.edcommerce.dto.ProductDTO;
import com.ednaldo.edcommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<ProductDTO> insertProduct(@RequestBody ProductDTO request) {
        ProductDTO productDTO = productService.insertProduct(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getProducts(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.getProductById(id));

    }
}
