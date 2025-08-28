package com.example.webbanghang.model.response;

public class GoogleUserInfo {
private String sub;
private String email;
private String name;
private String picture;
public GoogleUserInfo() {

} 
public GoogleUserInfo(String sub, String email, String name, String picture) {
    this.sub=sub;
    this.email= email;
    this.name = name;
    this.picture= picture;
}
public String getSub() {
    return this.sub;
} 
public String getEmail() {
    return this.email;
} 
public String getName() {
    return this.name;
} 
public String getPicture() {
    return this.picture;
}
}
