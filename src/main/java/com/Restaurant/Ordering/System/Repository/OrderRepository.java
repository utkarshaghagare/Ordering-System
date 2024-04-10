package com.Restaurant.Ordering.System.Repository;

import com.Restaurant.Ordering.System.Entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderHistory, Long> {
    // You can define custom query methods if needed
}

