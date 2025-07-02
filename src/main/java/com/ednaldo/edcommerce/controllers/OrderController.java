package com.ednaldo.edcommerce.controllers;

import com.ednaldo.edcommerce.dto.OrderDTO;
import com.ednaldo.edcommerce.dto.ProductDTO;
import com.ednaldo.edcommerce.dto.ProductMinDTO;
import com.ednaldo.edcommerce.services.OrderService;
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
@RequestMapping(value = "/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getProduct(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO dto) {
        return ResponseEntity.ok(orderService.insert(dto));
    }
}
