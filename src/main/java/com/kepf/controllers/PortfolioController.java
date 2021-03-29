package com.kepf.controllers;

import com.kepf.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/v1/portfolio")
public class PortfolioController {
    @Autowired
    PortfolioService portfolioService;
    @PostMapping("/{orderId}/{customerId}")
    public ResponseEntity<?>addPortfolio(@PathVariable Integer orderId, @PathVariable Integer customerId){
        return portfolioService.addPortfolio(orderId, customerId);
    }

    @GetMapping
    public ResponseEntity<?>allPortfolio(){
        return  portfolioService.allPortfolio();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?>allCustomerPortfolio(@PathVariable Integer customerId){
        return  portfolioService.allCustomerPortfolio(customerId);
    }

    @DeleteMapping("/delete/{portfolioId}")
    public ResponseEntity<?>deletePortfolio(@PathVariable Integer portfolioId){
        return portfolioService.deletePortfolio(portfolioId);
    }


}
