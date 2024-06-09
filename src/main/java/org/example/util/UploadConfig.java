package org.example.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig {

    @Value("${upload.dir}")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
}