package com.kepf.services;

import com.kepf.models.Customer;
import com.kepf.models.Orders;
import com.kepf.models.Portfolio;
import com.kepf.models.PortfolioRequest;
import com.kepf.repositories.CustomerRepository;
import com.kepf.repositories.OrderRepository;
import com.kepf.repositories.PortfolioRepository;
import com.kepf.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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

    public ResponseEntity<?> addPortfolio(PortfolioRequest portfolioRequest) {

        try {
            Portfolio portfolio = new Portfolio();
            Customer customer  = customerRepository.findById(portfolioRequest.getCustomer_id()).orElseThrow(()->new UsernameNotFoundException("no user record"));
            Orders orders = orderRepository.findById(portfolioRequest.getOrder_id()).orElseThrow(RuntimeException::new);
            Optional<Portfolio> portfolio1 = portfolioRepository.findByProduct(orders.getProduct());
            System.out.println(portfolio1);
            if(portfolio1.isPresent()){
                portfolio1.get().setQuantity(portfolio1.get().getQuantity()+orders.getQuantity());
                portfolioRepository.save(portfolio1.get());
                return ResponseEntity.ok(Helpers.apiResponse(200,"portfolio created",portfolio1));
            }
            portfolio.setCustomer(customer);
            portfolio.setProduct(orders.getProduct());
            portfolio.setQuantity(orders.getQuantity());
            Portfolio newPortfolio = portfolioRepository.save(portfolio);
            return ResponseEntity.ok(Helpers.apiResponse(200,"portfolio created",newPortfolio));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong while saving portfolio"+e, Collections.emptyList()));
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

    public ResponseEntity<?> allCustomerPortfolio(Integer customerId) {
        try{
            return ResponseEntity.ok(Helpers.apiResponse(200,"success",portfolioRepository.getCustomerPortfolio(customerId)));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }
    }
}
