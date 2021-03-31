package com.kepf.repositories;

import com.kepf.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    //@Query("SELECT o FROM orders o WHERE o.customer.id = ?1")
    Optional<List<Orders>> findByCustomerId(Integer customerId);

    @Query("SELECT o from orders o WHERE o.customer.id =?1 and o.is_success = true")
    Optional<List<Orders>>getUserSuccessfulOrders(Integer customerId);

    @Query("SELECT o from orders o WHERE o.customer.id =?1 and o.is_pending = true")
    Optional<List<Orders>>getUserPendingOrders(Integer customerId);

}
