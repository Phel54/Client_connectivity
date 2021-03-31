package com.kepf.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponse {
    private String jwt;
    private int id;
    private double account_balance;
    private String email;

}
