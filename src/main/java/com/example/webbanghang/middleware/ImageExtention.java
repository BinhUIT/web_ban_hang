package com.example.webbanghang.middleware;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import io.github.cdimascio.dotenv.Dotenv;

public class ImageExtention {
    
    public static String saveImage( MultipartFile multipartFile, String prefix) throws IOException {
        Dotenv dotenv = Dotenv.load();
        String rootFolder= dotenv.get("IMAGE_PATH");
        String folderName = rootFolder+prefix;
        File fol = new File(folderName);
        File dest = new File(fol, multipartFile.getOriginalFilename());
        multipartFile.transferTo(dest);
        return "unsecure/image/"+prefix+"/"+multipartFile.getOriginalFilename();


    }
}
