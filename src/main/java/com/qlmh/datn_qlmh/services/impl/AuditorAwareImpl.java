//package com.qlmh.datn_qlmh.services.impl;
//
//
//
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.data.domain.AuditorAware;
//
//import java.util.Optional;
//
//
//
//public class AuditorAwareImpl implements AuditorAware<String> {
//    // T phu thuoc vao kieu du lieu cua updateBy va createBy
////	@Autowired
////	private AccountRepo accountRepo;
//    @Override
//    public Optional<String> getCurrentAuditor() {
////		System.err.println("Accountrepo" + (accountRepo == null));
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication == null ? Optional.of("khong xac dinh") : Optional.ofNullable(authentication.getName());
//    }
//
//}