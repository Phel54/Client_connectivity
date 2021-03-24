package com.kepf.soap.client;

import com.kepf.models.Orders;
import com.kepf.wsdl.CustomerRequest;
import com.kepf.wsdl.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class SoapClient {
    @Autowired
    Jaxb2Marshaller jaxb2Marshaller;
    @Autowired
    private WebServiceTemplate webServiceTemplate;


    @Value("${soap-url}")
    private String soapUrl;
    public CustomerResponse sendOrderRequest(CustomerRequest request){
//        customerRequest.setCustomerId((request.getCustomer().getId()));
//        customerRequest.setPrice(request.getPrice());
//        customerRequest.setProduct(request.getProduct());
//        customerRequest.setSide(request.getSide());
//        customerRequest.setQuantity(request.getQuantity());
         return (CustomerResponse) webServiceTemplate.marshalSendAndReceive(soapUrl, request);
    }
}

