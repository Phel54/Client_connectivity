package com.kepf.repositories;

import com.kepf.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    @Query("SELECT p FROM portfolio p WHERE p.customer.id = ?1")
    List<Portfolio> getCustomerPortfolio(Integer customerId);

    @Query("SELECT p FROM portfolio p WHERE p.product = ?1")
    Optional<Portfolio>findByProduct(String product);
}
