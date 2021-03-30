package com.kepf.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PortfolioRequest {
    private Integer order_id;
    private Integer customer_id;
}
