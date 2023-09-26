package com.qlmh.datn_qlmh.security;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAccountDto {
    @NotBlank
    @Length(min = 5,max = 30)
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    @Length(min = 2,max = 15)
    private String phone;
    @NotBlank
    @Length(min = 5,max = 30)
    private String password;
    @NotBlank
    @Length(min = 5,max = 50)
    private String fullname;
    private String url;


}
