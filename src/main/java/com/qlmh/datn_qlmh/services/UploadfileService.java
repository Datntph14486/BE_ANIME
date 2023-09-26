package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.configs.UploadConfig;
import com.qlmh.datn_qlmh.ultil.UploadDownload;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UploadfileService {
    HttpServletRequest request;
    UploadConfig uploadConfig;

    UploadDownload uploadDownload;

    @Autowired
    RateImgService rateImgService;

    private  Path root = null;
    @Autowired
    public UploadfileService(UploadConfig uploadConfig,HttpServletRequest request,UploadDownload uploadDownload) {
        this.uploadConfig = uploadConfig;
        this.root = Paths.get(uploadConfig.getDirectory());
        this.request  = request;
        this.uploadDownload = uploadDownload;
    }


    public byte[] show(String url) throws IOException {
        Resource resource = uploadDownload.loadFile(url);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new RuntimeException("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return Files.readAllBytes(this.root.resolve(url));


    }

    public  String[] upload(MultipartFile[] files){
        String[] urls= new String[files.length];
        try {
            for(int i=0; i<urls.length;i++){
                try {
                    if (Files.exists(root)== false) {
                        Files.createDirectories(root);
                        System.err.println("Logger");
                    }
                    String url = Calendar.getInstance().getTime().getTime() + UUID.randomUUID().toString() +files[i].getOriginalFilename();
                    urls[i]=url;
                    Files.copy(files[i].getInputStream(), this.root.resolve( url), StandardCopyOption.REPLACE_EXISTING);
                } catch (IllegalStateException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            Arrays.asList(files).stream().forEach((item) -> {

            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return urls;


    }


}
