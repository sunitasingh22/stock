package com.portfolio.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("local")
public class AngularLocalConfig implements WebMvcConfigurer{

	   @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**") // Allow CORS for all endpoints
	                .allowedOrigins("http://localhost:4200") // Allow this origin
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify allowed methods
	                .allowedHeaders("*") // Allow all headers
	                .allowCredentials(true); // If needed, allow credentials
	    }

}
