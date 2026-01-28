package com.semihsahinoglu.standing_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoAuthFeignConfig {

    @Bean
    public RequestInterceptor serviceNameInterceptor() {
        return template -> template.header("X-Service-Name", "standing-service");
    }
}