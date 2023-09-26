package com.qlmh.datn_qlmh.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AuthenticationSerive {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenRepo refreshTokenRepo;
    @Autowired
    AccountRoleRepo accountRoleRepo;
    @Autowired
    private JwtService jwtService;
    public TokenDto authenticate(LoginDto loginDto){
        Authentication authentication=null;
        try {
            authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            throw exception;
        }
        String access_token = jwtService.generateToken(loginDto.getUsername(),authentication);
        String refresh_token = jwtService.generateRefreshToken(loginDto.getUsername(),authentication);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccess_token(access_token);
        tokenDto.setAccess_token_expired(jwtService.extractAllClaims(access_token,true).getExpiration());
        tokenDto.setRefresh_token(refresh_token);
        tokenDto.setRefresh_token_expired(jwtService.extractAllClaims(refresh_token,false).getExpiration());
        refreshTokenRepo.save(new RefreshTokenEntity(null,refresh_token, loginDto.getUsername()));
        String scopes[] = authentication.getAuthorities().stream().map((authority)->authority.getAuthority()).toArray(size ->new String[size]);
        for (int i = 0; i < scopes.length; i++) {
            scopes[i] = scopes[i].substring("ROLE_".length());
        }
        tokenDto.setRole(scopes);
        return tokenDto;
    }
    public void logout(TokenDto tokenDto){
        Optional<RefreshTokenEntity> optional = refreshTokenRepo.findByRefreshToken(tokenDto.getRefresh_token());
        if(optional.isPresent() && jwtService.validateRefreshToken(tokenDto.getRefresh_token())){
            int deletedRow = refreshTokenRepo.deleteRTByRefreshToken(optional.get().getRefreshToken());
            log.info("Deleted row when logout : {}",deletedRow);
        }
        else{
            throw new BadCredentialsException("Refresh token is invalid or expired");
        }
    }
    public void logoutAll(TokenDto tokenDto){
        Optional<RefreshTokenEntity> optional = refreshTokenRepo.findByRefreshToken(tokenDto.getRefresh_token());
        if( optional.isPresent() && jwtService.validateRefreshToken(tokenDto.getRefresh_token())){
            int deletedRow = refreshTokenRepo.deleteByUsername(optional.get().getUsername());
            log.info("Deleted row when logout all : {}",deletedRow);
        }
        else{
            throw new BadCredentialsException("Refresh token is invalid or expired");
        }
    }
    public TokenDto refresh(TokenDto tokenDto){
        Optional<RefreshTokenEntity> optional = refreshTokenRepo.findByRefreshToken(tokenDto.getRefresh_token());
        if( optional.isPresent() && jwtService.validateRefreshToken(tokenDto.getRefresh_token())){
            String username = jwtService.getUsernameFromJwtToken(tokenDto.getRefresh_token(),false);
            TokenDto dto = new TokenDto();
            String access_token = jwtService.generateToken(username,accountRoleRepo.getAccountRoleEntityByUserName(username));
            dto.setRefresh_token(tokenDto.getRefresh_token());
            dto.setAccess_token_expired(jwtService.extractAllClaims(access_token,true).getExpiration());
            dto.setAccess_token(access_token);
            return dto;
        }
        else{
            throw new BadCredentialsException("Refresh token is invalid or expired");
        }
    }
}
