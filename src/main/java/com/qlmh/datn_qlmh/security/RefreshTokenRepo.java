package com.qlmh.datn_qlmh.security;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity,Long> {
    @Query("SELECT rte FROM RefreshTokenEntity rte WHERE rte.refreshToken =:rt")
    public Optional<RefreshTokenEntity> findByRefreshToken(@Param("rt") String refreshToken);
    @Query("DELETE FROM RefreshTokenEntity rte WHERE rte.refreshToken =:rt")
    @Modifying()
    @Transactional
    public int deleteRTByRefreshToken(@Param("rt") String refreshToken);
    @Modifying()
    @Transactional
    @Query("DELETE FROM RefreshTokenEntity rte WHERE rte.username =:us")
    public int deleteByUsername(@Param("us") String username);

}
