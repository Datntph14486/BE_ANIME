package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.entities.DeliveryStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeliveryService {
    private RestTemplate restTemplate;

    public DeliveryService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getOrderDetails() {
        String url = "https://api.deliverycompany.com/orders";
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }
    public DeliveryStatus getDeliveryStatus(String trackingNumber) {
        String url = "https://api.deliverycompany.com/tracking?number=" + trackingNumber;
        DeliveryStatus deliveryStatus = restTemplate.getForObject(url, DeliveryStatus.class);
        return deliveryStatus;
    }
}
