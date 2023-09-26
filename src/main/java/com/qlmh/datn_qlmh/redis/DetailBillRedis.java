package com.qlmh.datn_qlmh.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class DetailBillRedis {
    @Id
    @Indexed
    private String id;
    @Indexed
    private Integer productId;
    @Indexed
    private Integer idDetail;
    @Indexed
    private int amount;
    @Indexed
    private BigDecimal price;
    @Indexed
    private BigDecimal priceSale;
    @Indexed
    private Integer billId;

}
