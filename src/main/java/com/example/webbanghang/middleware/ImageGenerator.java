package com.example.webbanghang.middleware;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Component;

//@Component
public class ImageGenerator {
    public ImageGenerator() {
        int startIndex=300;
        for(int i=10;i<=231;i++) {
            String imageURL="https://picsum.photos/300/"+startIndex+".jpg";
            String destinationFile = "D:/fashion_image/product_variant/"+i+".jpg"; 
             try {
            saveImageFromUrl(imageURL, destinationFile);
            
        } catch (IOException e) {
            System.err.println("Error downloading image: " + e.getMessage());
            e.printStackTrace();
        }
        }
    } 
    public static void saveImageFromUrl(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream is = url.openStream();
             FileOutputStream fos = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}
