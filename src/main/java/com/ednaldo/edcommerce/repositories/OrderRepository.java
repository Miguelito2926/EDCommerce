package com.ednaldo.edcommerce.repositories;

import com.ednaldo.edcommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
