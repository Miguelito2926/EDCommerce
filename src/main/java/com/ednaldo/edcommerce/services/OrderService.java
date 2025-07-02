package com.ednaldo.edcommerce.services;

import com.ednaldo.edcommerce.dto.OrderDTO;
import com.ednaldo.edcommerce.dto.OrderItemDTO;
import com.ednaldo.edcommerce.entities.Order;
import com.ednaldo.edcommerce.entities.OrderItem;
import com.ednaldo.edcommerce.entities.Product;
import com.ednaldo.edcommerce.entities.enums.OrderStatus;
import com.ednaldo.edcommerce.repositories.OrderItemRepository;
import com.ednaldo.edcommerce.repositories.OrderRepository;
import com.ednaldo.edcommerce.repositories.ProductRepository;
import com.ednaldo.edcommerce.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final AuthService authService;

    public OrderService(
            OrderRepository orderRepository,
            UserService userService,
            ProductRepository productRepository,
            OrderItemRepository orderItemRepository,
            AuthService authService
    ) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.authService = authService;
    }

    @Transactional
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Not Found Id: " + id));

        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setClient(userService.authenticated());

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());
        return new OrderDTO(order);
    }
}
