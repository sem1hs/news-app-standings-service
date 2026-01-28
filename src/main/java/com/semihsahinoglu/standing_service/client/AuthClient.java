package com.semihsahinoglu.standing_service.client;

import com.semihsahinoglu.standing_service.config.GlobalFeignConfig;
import com.semihsahinoglu.standing_service.config.NoAuthFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-service", url = "http://localhost:8081", configuration = {NoAuthFeignConfig.class, GlobalFeignConfig.class})
public interface AuthClient {

    @PostMapping("/internal/auth/service-token")
    String getServiceToken();
}
