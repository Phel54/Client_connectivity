package com.kepf.utils;
import com.kepf.models.Customer;
import com.kepf.models.Orders;
import com.kepf.wsdl.CustomerRequest;

import java.util.HashMap;
import java.util.Map;

public class Helpers {

    public static Object apiResponse(int status, String message, Object datas){
        Map<String, Object> data = new HashMap<>();
        data.put("status",status);
        data.put("message",message);
        data.put("data", datas);
        data.put("url","");
        return data;
    }

    public static Orders newOrder(CustomerRequest request, Customer customer){
        Orders orders1 = new Orders();
        orders1.setCustomer(customer);
        orders1.setProduct(request.getProduct());
        orders1.setPrice(request.getPrice());
        orders1.setQuantity(request.getQuantity());
        orders1.setSide(request.getSide());

        return orders1;
    }
}
