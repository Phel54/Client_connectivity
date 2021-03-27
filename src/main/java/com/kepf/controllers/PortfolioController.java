package com.kepf.controllers;

import com.kepf.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/api/v1/portfolio")
@CrossOrigin(originPatterns = "*")
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

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?>deletePortfolio(@PathVariable Integer portfolioId){
        return portfolioService.deletePortfolio(portfolioId);
    }


}
