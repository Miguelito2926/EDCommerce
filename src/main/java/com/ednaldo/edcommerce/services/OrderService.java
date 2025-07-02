package com.ednaldo.edcommerce.services;

import com.ednaldo.edcommerce.dto.OrderDTO;
import com.ednaldo.edcommerce.entities.Order;
import com.ednaldo.edcommerce.repositories.OrderRepository;
import com.ednaldo.edcommerce.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found Id: " + id));

        return new OrderDTO(order);
    }
}
