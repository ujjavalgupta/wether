package com.ujjaval.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Configuration
@Data
public class AppConfig {

    
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
}
