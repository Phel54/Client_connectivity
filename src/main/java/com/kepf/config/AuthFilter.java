package com.kepf.config;


import com.kepf.services.JwtUserDetailService;

import com.kepf.services.MyUserDetails;
import com.kepf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtUserDetailService jwtUserDetailService;

    public AuthFilter(JwtUtil jwtUtil, JwtUserDetailService jwtUserDetailService){
        this.jwtUtil=jwtUtil;
        this.jwtUserDetailService = jwtUserDetailService;
    }

   private String email;
    private String token;
    private  MyUserDetails userDetails;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
       return path.matches("/api/v1/customer/register")||path.matches("/api/v1/customer/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "no token provided");
            return;
        }

        String[] authHeaderArray = authHeader.split("Bearer ");
        if (authHeaderArray[1]==null)
            response.sendError(HttpServletResponse.SC_FORBIDDEN,"no token available");

         this.token = authHeaderArray[1];


        try {
            this.email = jwtUtil.extractEmail(this.token);

        }catch (Exception e){

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "error extracting email");
        }

       this.userDetails = (MyUserDetails) jwtUserDetailService.loadUserByUsername(this.email);

        if(jwtUtil.validateToken(token, this.userDetails)){
            request.setAttribute("email", this.email);
        }

        filterChain.doFilter(request,response);
    }
}
