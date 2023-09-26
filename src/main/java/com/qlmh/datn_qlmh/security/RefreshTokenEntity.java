package com.qlmh.datn_qlmh.security;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "refresh_token")
@ToString
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "username")
    private String username;



}
