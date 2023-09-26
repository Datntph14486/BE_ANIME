package com.qlmh.datn_qlmh.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String oldPassword;
    @NotBlank
    @NotNull
    private String newPassword;
    @NotBlank
    @NotNull
    private String confirmPassword;
}
