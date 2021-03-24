package com.kepf.services;

import com.kepf.exceptions.UsersException;
import com.kepf.models.AuthRequest;
import com.kepf.models.AuthResponse;
import com.kepf.models.Customer;
import com.kepf.models.Orders;
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
import javax.security.auth.login.LoginException;
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
           //throw new LoginException("wrong username and password" + exception.getMessage());
       }
       try{
           final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
           final String jwt = jwtUtil.generateToken((MyUserDetails) userDetails);
           return ResponseEntity.ok(Helpers.apiResponse(200,"order created",new AuthResponse(jwt)));
       }catch (Exception e){
           return ResponseEntity.status(400).body(Helpers.apiResponse(400,"sorry something went wrong", Collections.emptyList()));
       }


    }

    public ResponseEntity<?> register(Customer customer) {

//        if(!EmailValidation.isEmailValid(users.getEmail()))
//            throw new UsersException("invalid email address");

        Optional<Customer> customerOptional = customerRepository.findByEmail(customer.getEmail());
        if(customerOptional.isPresent())
            return ResponseEntity.badRequest().body(Helpers.apiResponse(400,"sorry user already exist", Collections.emptyList()));
           // throw new UsersException("user already exist");

        try {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));

            Customer newCustomer = customerRepository.save(customer);
            return ResponseEntity.ok(Helpers.apiResponse(200,"order created",newCustomer));

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
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<?> topUpBalance(Integer customerId, double amount)  {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty())
            return ResponseEntity.badRequest().body(Helpers.apiResponse(400,"sorry no user exist",Collections.singletonList(customer)));
        customer.get().setAccount_balance(amount);
        customerRepository.save(customer.get());
        return ResponseEntity.ok(Helpers.apiResponse(200,"success",Collections.singletonList(customer.get())));


    }
}
