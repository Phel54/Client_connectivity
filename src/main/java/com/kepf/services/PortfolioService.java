package com.kepf.services;

import com.kepf.models.Customer;
import com.kepf.models.Orders;
import com.kepf.models.Portfolio;
import com.kepf.repositories.CustomerRepository;
import com.kepf.repositories.OrderRepository;
import com.kepf.repositories.PortfolioRepository;
import com.kepf.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Service
public class PortfolioService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PortfolioRepository portfolioRepository;

    public ResponseEntity<?> addPortfolio(Integer orderId, Integer customerId) {

        try {
            Portfolio portfolio = new Portfolio();
            Customer customer  = customerRepository.findById(customerId).orElseThrow(()->new UsernameNotFoundException("no user recorde"));
            Orders orders = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);
            portfolio.setCustomer(customer);
            portfolio.setProduct(orders.getProduct());
            portfolio.setQuantity(orders.getQuantity());
            Portfolio newPortfolio = portfolioRepository.save(portfolio);
            return ResponseEntity.ok(Helpers.apiResponse(200,"order created",newPortfolio));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong while saving orders", Collections.emptyList()));
        }

    }

    public ResponseEntity<?> allPortfolio() {
        try{
            return ResponseEntity.ok(Helpers.apiResponse(200,"success",portfolioRepository.findAll()));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }
    }

    public ResponseEntity<?> deletePortfolio(Integer portfolioId) {
        try{
            portfolioRepository.deleteById(portfolioId);
            return ResponseEntity.ok(Helpers.apiResponse(200,"success","portfolio deleted"));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry error deleting portfolio", Collections.emptyList()));
        }
    }
}
