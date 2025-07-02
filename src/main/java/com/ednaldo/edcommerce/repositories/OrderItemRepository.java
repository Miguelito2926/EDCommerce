package com.ednaldo.edcommerce.repositories;

import com.ednaldo.edcommerce.entities.Order;
import com.ednaldo.edcommerce.entities.OrderItem;
import com.ednaldo.edcommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
