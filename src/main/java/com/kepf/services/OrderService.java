package com.kepf.services;
import com.kepf.models.Customer;
import com.kepf.models.Orders;
import com.kepf.repositories.OrderRepository;
import com.kepf.repositories.CustomerRepository;
import com.kepf.soap.client.SoapClient;
import com.kepf.utils.Helpers;
import com.kepf.wsdl.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    SoapClient soapClient;

    @Async
    public ResponseEntity<?> createOrder(CustomerRequest orders){
        Optional<Customer> customer  = customerRepository.findById(orders.getCustomerId());
        if(customer.isEmpty())
            return ResponseEntity.notFound().build();

        soapClient.sendOrderRequest(orders);
        try{
            Orders newOrders = orderRepository.save((Helpers.newOrder(orders, customer.get())));
            return ResponseEntity.ok(Helpers.apiResponse(200,"order created",newOrders));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }


    }

    public ResponseEntity<?> orderById(Integer orderId) {
        try{
            Optional<Orders> order = orderRepository.findById(orderId);
            return ResponseEntity.ok(Helpers.apiResponse(200,"success",Collections.singletonList(order)));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }
    }


    public ResponseEntity<?> userOrders(Integer customerId) {
        try{
            Optional<Orders> userOrders = orderRepository.findByCustomerId(customerId);
            return ResponseEntity.ok(Helpers.apiResponse(200,"success",Collections.singletonList(userOrders)));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }


        //return userOrders.isEmpty()?ResponseEntity.noContent().build():ResponseEntity.ok();
    }

    public ResponseEntity<?> allOrders() {
        try{
            return ResponseEntity.ok(Helpers.apiResponse(200,"success",orderRepository.findAll()));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }
    }
}
