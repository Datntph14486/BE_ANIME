package com.qlmh.datn_qlmh.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForgetPasswordto {
    @NotBlank
    @NotNull
    String token;
    @NotBlank
    @NotNull
    String newPassword;
    @NotBlank
    @NotNull
    String confirm;

}
