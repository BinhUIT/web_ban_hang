package com.example.webbanghang.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.webbanghang.middleware.Constants;


@RestController
public class AssetController {
    @GetMapping("/unsecure/image/{folder}/{name}")
    public ResponseEntity<Resource> getMethodName(@PathVariable String folder, @PathVariable String name) {
        Path path = Paths.get(Constants.imageDir+folder+"/"+name);
        try {
            Resource resource = new UrlResource(path.toUri()); 
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }

    }
    
}
