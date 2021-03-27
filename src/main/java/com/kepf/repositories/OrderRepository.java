package com.kepf.repositories;

import com.kepf.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT u FROM orders u WHERE u.customer.id = ?1")
    List<Orders> findByCustomerId(Integer customerId);

    @Query("SELECT o from orders o WHERE o.customer.id =?1 and o.is_success = true")
    List<Orders>getUserSuccessfulOrders(Integer customerId);

    @Query("SELECT o from orders o WHERE o.customer.id =?1 and o.is_pending = true")
    List<Orders>getUserPendingOrders(Integer customerId);

}
