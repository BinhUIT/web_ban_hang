package com.example.webbanghang.service.productservice;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.webbanghang.model.response.DetectResult;
import com.example.webbanghang.model.selectcolumninterface.ProductMiniInfo;
import com.example.webbanghang.repository.ProductRepository;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class ProductRecommendationService {
    private final Dotenv dotenv = Dotenv.load();

    private final ProductRepository productRepo;
    public ProductRecommendationService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    } 
    public List<ProductMiniInfo> analyzeImage(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
        @Override
        public String getFilename() {
            return file.getOriginalFilename();
        }
    };
        body.add("file",fileResource);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);
        String pythonURL = dotenv.get("RECOMMENDATION_SERVER");
        ResponseEntity<DetectResult> result = restTemplate.postForEntity(pythonURL,requestEntity,DetectResult.class);
        DetectResult data= result.getBody();
        String[] analyzeData = getInfoFromData(data);
        List<ProductMiniInfo> res = productRepo.findByForAgeOrForGenderOrForShape(analyzeData[0], analyzeData[1], analyzeData[2]);
        return res;
        

    }
    private final String[] getInfoFromData(DetectResult data) {
        String[] res = new String[3];
        if(data.getAge()<15) {
            res[0]="Child";
        }
        if(data.getAge()>=15&&data.getAge()<40) {
            res[0]="Young";
        } 
        if(data.getAge()>=40&&data.getAge()<70){
            res[0]="Middle";
        }
        if(data.getAge()>=70) {
            res[0]="Old";
        }
        res[1]= data.getGender()==false?"Male":"Female";
        res[2]=data.getBody_shape();
        return res;
    }
}
