package com.SkillExchange.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Configuration
public class WebConfig2 implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	 System.out.println("CORS configuration is being applied..."); 
        registry.addMapping("/api/**")  // Allows CORS for all API endpoints
                .allowedOrigins("http://localhost:5173")  // Replace with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true); 
    }
}
