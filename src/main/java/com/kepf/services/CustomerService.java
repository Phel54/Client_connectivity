package com.kepf.services;

import com.kepf.models.*;
import com.kepf.repositories.CustomerRepository;
import com.kepf.utils.Helpers;
import com.kepf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;


@Service
public class CustomerService {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtUserDetailService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<?> login(AuthRequest authRequest){
       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
       }catch (BadCredentialsException exception){
           return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));

       }
       try{
           final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
           final String jwt = jwtUtil.generateToken((MyUserDetails) userDetails);
           return ResponseEntity.ok(Helpers.apiResponse(200,"login successful",new AuthResponse(jwt,((MyUserDetails) userDetails).getId(),((MyUserDetails) userDetails).getAccount_balance(),((MyUserDetails) userDetails).getEmail())));
       }catch (Exception e){
           return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
       }

    }

    public ResponseEntity<?> register(Customer customer) {

        Optional<Customer> customerOptional = customerRepository.findByEmail(customer.getEmail());
        if(customerOptional.isPresent())
            return ResponseEntity.badRequest().body(Helpers.apiResponse(400,"sorry user already exist", Collections.emptyList()));

        try {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));

            Customer newCustomer = customerRepository.save(customer);
            return ResponseEntity.ok(Helpers.apiResponse(200,"user created",newCustomer));

        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }

    }

    public ResponseEntity<?> customerById(Integer customerId) {

        try{
            Optional<Customer> customer = customerRepository.findById(customerId);
            return ResponseEntity.ok(Helpers.apiResponse(200,"success",Collections.singletonList(customer)));
        }catch (Exception e){
            return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
        }

    }

    public ResponseEntity<Object> deleteAccount(Integer customerId) {
        customerRepository.deleteById(customerId);
        return ResponseEntity.ok(Helpers.apiResponse(200,"success deleting account",Collections.emptyList()));
    }

    @Transactional
    public ResponseEntity<?> topUpBalance(AccountRequest accountRequest, Integer customerId )  {
        Customer customer = customerRepository.findById(customerId).orElse(null);
//        if(customer.isEmpty())
//            return ResponseEntity.badRequest().body(Helpers.apiResponse(400,"sorry no user exist",Collections.singletonList(customer)));
        assert customer != null;
        customer.setAccount_balance(customer.getAccount_balance()+
                accountRequest.getAccount_balance());
        customerRepository.save(customer);
        return ResponseEntity.ok(Helpers.apiResponse(200,"success",customer));


    }
}
