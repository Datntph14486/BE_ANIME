package com.qlmh.datn_qlmh.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class FontEndConfig {
    @Value("${font_end.url}")
    String url;
}
