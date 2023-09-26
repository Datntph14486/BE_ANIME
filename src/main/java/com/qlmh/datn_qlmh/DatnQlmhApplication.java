package com.qlmh.datn_qlmh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
@EnableCaching
public class DatnQlmhApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatnQlmhApplication.class, args);
    }

}
