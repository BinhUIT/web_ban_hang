package com.example.webbanghang.model.entity;

import java.util.Date;

import com.example.webbanghang.model.enums.EHttpMethod;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="LOG")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;
    private EHttpMethod method;
    private String url_api;
    private String responseMessage;
    private int code;
    private String status;
    private Date createAt;
    private long userId;
    private String ip;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public EHttpMethod getMethod() {
        return method;
    }
    public void setMethod(EHttpMethod method) {
        this.method = method;
    }
    public String getUrl_api() {
        return url_api;
    }
    public void setUrl_api(String url_api) {
        this.url_api = url_api;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public Log() {
    }
    public Log(int id, EHttpMethod method, String url_api, String responseMessage, int code, String status,
            Date createAt, long userId, String ip) {
        this.id = id;
        this.method = method;
        this.url_api = url_api;
        this.responseMessage = responseMessage;
        this.code = code;
        this.status = status;
        this.createAt = createAt;
        this.userId = userId;
        this.ip = ip;
    }

}
