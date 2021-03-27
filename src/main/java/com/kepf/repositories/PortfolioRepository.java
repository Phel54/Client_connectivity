package com.kepf.repositories;

import com.kepf.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
}
