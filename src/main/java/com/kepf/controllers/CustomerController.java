package com.kepf.controllers;


import com.kepf.models.AccountRequest;
import com.kepf.models.AuthRequest;
import com.kepf.models.Customer;
import com.kepf.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        return customerService.login(authRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        return customerService.register(customer);
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<?> customerById(@PathVariable Integer customerId) {

        return  customerService.customerById(customerId);

    }

    @PutMapping("/topup/balance/{customerId}")
    public ResponseEntity<?> topUpBalance(@RequestBody AccountRequest accountRequest, @PathVariable Integer customerId) {
        return customerService.topUpBalance(accountRequest, customerId);
    }

    @DeleteMapping("/delete/account/{customerId}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Integer customerId){
        return customerService.deleteAccount(customerId);
    }
}
