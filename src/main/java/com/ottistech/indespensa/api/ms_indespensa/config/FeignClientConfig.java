package com.ottistech.indespensa.api.ms_indespensa.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = "EB2BD5695183B96869FA1C726C3F5D25";
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}