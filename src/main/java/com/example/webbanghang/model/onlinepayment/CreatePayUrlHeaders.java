package com.example.webbanghang.model.onlinepayment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.cdimascio.dotenv.Dotenv;

public class CreatePayUrlHeaders {
    @JsonProperty("x-client-id")
    private String xClientId;
    @JsonProperty("x-api-key")
    private String xApiKey;
    public CreatePayUrlHeaders() {
        
    }

    public CreatePayUrlHeaders(String xApiKey, String xClientId) {
        this.xApiKey = xApiKey;
        this.xClientId = xClientId;
    }
    @JsonIgnore
    public static CreatePayUrlHeaders getInstance() {
        Dotenv dotenv = Dotenv.load();
        return new CreatePayUrlHeaders(dotenv.get("PAYOS_API_KEY"),dotenv.get("PAYOS_CLIENT_ID"));
    }

    public String getxClientId() {
        return xClientId;
    }

    public void setxClientId(String xClientId) {
        this.xClientId = xClientId;
    }

    public String getxApiKey() {
        return xApiKey;
    }

    public void setxApiKey(String xApiKey) {
        this.xApiKey = xApiKey;
    }
}
