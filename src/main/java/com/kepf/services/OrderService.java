package com.kepf.services;
import com.kepf.models.Customer;
import com.kepf.models.Orders;
import com.kepf.repositories.OrderRepository;
import com.kepf.repositories.CustomerRepository;
import com.kepf.soap.client.SoapClient;
import com.kepf.utils.Helpers;
import com.kepf.wsdl.CustomerRequest;
import com.kepf.wsdl.CustomerResponse;
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

    public ResponseEntity<?> createOrder(CustomerRequest orders){
        Optional<Customer> customer  = customerRepository.findById(orders.getCustomerId());
        if(customer.isEmpty())
            return ResponseEntity.notFound().build();

        try{
            CustomerResponse response = soapClient.sendOrderRequest(orders);
            return ResponseEntity.ok(Helpers.apiResponse(200,"order submitted", orders));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong while submitting order", Collections.emptyList()));
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
        List<Orders> userOrders = orderRepository.findByCustomerId(customerId);
        return ResponseEntity.ok(Helpers.apiResponse(200,"success",userOrders));
    }

    public ResponseEntity<?> allOrders() {
        try{
            return ResponseEntity.ok(Helpers.apiResponse(200,"success",orderRepository.findAll()));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }
    }

    public ResponseEntity<?> successfulUserOrders(Integer customerId) {
        List<Orders> userOrders = orderRepository.getUserSuccessfulOrders(customerId);
        return ResponseEntity.ok(Helpers.apiResponse(200,"success",userOrders));
    }

    public ResponseEntity<?> pendingUserOrders(Integer customerId) {
        List<Orders> userOrders = orderRepository.getUserPendingOrders(customerId);
        return ResponseEntity.ok(Helpers.apiResponse(200,"success",userOrders));
    }
}
