package com.qlmh.datn_qlmh.dtos.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PaymentResponse {
    private String paymentUrl;
}
