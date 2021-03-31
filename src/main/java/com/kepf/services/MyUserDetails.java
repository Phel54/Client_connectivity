package com.kepf.services;

import com.kepf.models.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MyUserDetails implements UserDetails {
    private String email;
    private String password;
    private int id;
    private boolean is_active;
    private double account_balance;

    public MyUserDetails(Customer customer){
        this.email = customer.getEmail();
        this.id = customer.getId();
        this.password = customer.getPassword();
        this.account_balance =customer.getAccount_balance();

    }
    public MyUserDetails(){

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    public double getAccount_balance() {
        return account_balance;
    }

    public String getEmail(){
        return this.email;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
