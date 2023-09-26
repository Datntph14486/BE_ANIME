package com.qlmh.datn_qlmh.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {
    private String directory;
    public String getDirectory() {
        return this.directory;
    }
    public void setDirectory(String dir) {
        this.directory = dir;
    }
}
