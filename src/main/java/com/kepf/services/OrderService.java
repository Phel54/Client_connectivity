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
            return ResponseEntity.status(404).body(Helpers.apiResponse(404,"sorry no customer exist", Collections.emptyList()));

        try{
            System.out.println("before");
            CustomerResponse response = soapClient.sendOrderRequest(orders);
            System.out.println("response");
            return ResponseEntity.ok(Helpers.apiResponse(200,"order submitted", orders));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong while submitting order"+e, Collections.emptyList()));
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
        Optional<List<Orders>> userOrders = orderRepository.findByCustomerId(customerId);
        if(userOrders.isEmpty())
            return ResponseEntity.status(400).body(Helpers.apiResponse(404,"no record found", Collections.emptyList()));

        System.out.println(userOrders.get());
        return ResponseEntity.ok(Helpers.apiResponse(200,"success",userOrders.get()));
    }

    public ResponseEntity<?> allOrders() {
        try{
            return ResponseEntity.ok(Helpers.apiResponse(200,"success",orderRepository.findAll()));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }
    }

    public ResponseEntity<?> successfulUserOrders(Integer customerId) {
        Optional<List<Orders>> userOrders = orderRepository.getUserSuccessfulOrders(customerId);
        if(userOrders.isEmpty())
            return ResponseEntity.status(400).body(Helpers.apiResponse(404,"no record found", Collections.emptyList()));
        return ResponseEntity.ok(Helpers.apiResponse(200,"success",userOrders));
    }

    public ResponseEntity<?> pendingUserOrders(Integer customerId) {
        Optional<List<Orders>> userOrders = orderRepository.getUserPendingOrders(customerId);
        if(userOrders.isEmpty())
            return ResponseEntity.status(400).body(Helpers.apiResponse(404,"no record found", Collections.emptyList()));
        return ResponseEntity.ok(Helpers.apiResponse(200,"success",userOrders));
    }
}
