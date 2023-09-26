package com.qlmh.datn_qlmh.security;

import com.qlmh.datn_qlmh.entities.AccountEntity;
import com.qlmh.datn_qlmh.entities.AccountRoleEntity;
import com.qlmh.datn_qlmh.entities.RoleEntity;
import com.qlmh.datn_qlmh.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AccountDetailService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AccountRoleRepo accountRoleRepo;
    private final String prefix = "ROLE_";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity account = accountRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found ", username)));
        return new User(account.getUserName(),account.getPassword(),mapRoleToAuthority(accountRoleRepo.getAccountRoleEntityByUserName(account.getUserName())));
    }
    private Collection<GrantedAuthority> mapRoleToAuthority(List<AccountRoleEntity> list) {
        return list.stream().map(role -> new SimpleGrantedAuthority(prefix+role.getRoleEntity().getRoleName())).collect(Collectors.toList());
    }
}
