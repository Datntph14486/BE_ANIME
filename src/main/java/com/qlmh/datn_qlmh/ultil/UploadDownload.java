package com.qlmh.datn_qlmh.ultil;


import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.qlmh.datn_qlmh.configs.UploadConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UploadDownload {
    private final Path fileStorageLocation;
    public UploadDownload(UploadConfig uploadConfig) {
        this.fileStorageLocation = Paths.get(uploadConfig.getDirectory())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    public Resource loadFile(String fileName) {
        Path path = fileStorageLocation.resolve(fileName).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()) {
                return resource;
            }
            throw new RuntimeException("File not found");
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found");
        }

    }
}
