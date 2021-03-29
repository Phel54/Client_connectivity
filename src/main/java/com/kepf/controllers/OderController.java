package com.kepf.controllers;

import com.kepf.services.OrderService;
import com.kepf.wsdl.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/order")
public class OderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<?>createOrder(@RequestBody CustomerRequest orders){
        return orderService.createOrder(orders);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?>orderById(@PathVariable Integer orderId){
        return orderService.orderById(orderId);
    }

    @GetMapping
    public ResponseEntity<?>allOrders(){
        return orderService.allOrders();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?>userOrders(@PathVariable Integer customerId){
        return orderService.userOrders(customerId);
    }

    @GetMapping("/customer/order/complete/{customerId}")
    public ResponseEntity<?>successfulUserOrders(@PathVariable Integer customerId){
        return orderService.successfulUserOrders(customerId);
    }

    @GetMapping("/customer/order/pending/{customerId}")
    public ResponseEntity<?>pendingUserOrders(@PathVariable Integer customerId){
        return orderService.pendingUserOrders(customerId);
    }
}
