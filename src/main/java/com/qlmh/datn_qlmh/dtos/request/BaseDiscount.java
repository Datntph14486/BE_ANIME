package com.qlmh.datn_qlmh.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qlmh.datn_qlmh.constants.Constant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseDiscount {
    @NotNull
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING,timezone = "Asia/Saigon")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    protected Date discountStart;
    @NotNull
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING,timezone = "Asia/Saigon")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    protected Date discountEnd;
    @NotNull
//    @Size(min = 0,max = 1)
    protected Byte discountType;
    @NotNull
    protected BigDecimal discountAmount;


}
