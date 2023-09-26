package com.qlmh.datn_qlmh.configs;

//import com.qlmh.datn_qlmh.loader.MailConfig;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
public class BeanConfig {
//    @Autowired
//    MailConfig mailConfig;
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper= new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD).setDeepCopyEnabled(true);
        return  mapper;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(mailConfig.getHost());
//        mailSender.setPort(mailConfig.getPort());
//        mailSender.setUsername(mailConfig.getUsername());
//        mailSender.setPassword(mailConfig.getPassword());
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.auth", mailConfig.getSmtp_auth());
//        properties.setProperty("mail.smtp.starttls.enable", mailConfig.getSmtp_starttls_enable());
//        mailSender.setJavaMailProperties(properties);
//        return mailSender;
//    }
//    @Bean
//    public JavaMailSender getJavaMailSender(){
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        return javaMailSender;
//    }
}
