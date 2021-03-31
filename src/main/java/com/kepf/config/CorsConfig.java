package com.kepf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET","POST","PATCH","DELETE","PUT","OPTIONS")
                        .allowedHeaders("append","delete","entries","foreach","get","has","keys","set","values","Authorization", "Access-Control-Allow-Origin","content-type")

                        .allowCredentials(true);
            }
        };
    }
}
