package com.kepf;

import com.kepf.config.AuthFilter;
import com.kepf.services.JwtUserDetailService;
import com.kepf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ClientConnectivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientConnectivityApplication.class, args);
	}

	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	JwtUserDetailService jwtUserDetailService;
	@Bean
	public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean(){
		FilterRegistrationBean<AuthFilter>registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter(jwtUtil, jwtUserDetailService);
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/v1/customer/*");
		registrationBean.addUrlPatterns("/api/v1/order/*");
		return registrationBean;
	}



}
