package com.kepf.controllers;

import com.kepf.models.Orders;
import com.kepf.services.OrderService;
import com.kepf.wsdl.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/customer/{userId}")
    public ResponseEntity<?>userOrders(@PathVariable Integer userId){
        return orderService.userOrders(userId);
    }
}
