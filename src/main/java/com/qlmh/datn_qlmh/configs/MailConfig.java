package com.qlmh.datn_qlmh.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class MailConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
