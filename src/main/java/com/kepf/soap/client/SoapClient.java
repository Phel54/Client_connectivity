package com.kepf.soap.client;

import com.kepf.models.Orders;
import com.kepf.wsdl.CustomerRequest;
import com.kepf.wsdl.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public CustomerResponse sendOrderRequest(CustomerRequest request){

         return (CustomerResponse) webServiceTemplate.marshalSendAndReceive(soapUrl, request);
    }
}

