package com.example.webbanghang.model.entity;

import java.util.Date;

import com.example.webbanghang.model.enums.ENotificationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="NOTIFICATIONS")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    @ManyToOne
    @JoinColumn(name="user_id") 
    private User user;
    private String content;
    private String title;
    private ENotificationStatus status;
    private Date createAt;
    private Date updateAt;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public ENotificationStatus getStatus() {
        return status;
    }
    public void setStatus(ENotificationStatus status) {
        this.status = status;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Date getUpdateAt() {
        return updateAt;
    }
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
    public Notification() {
    }
    public Notification(int id, User user, String content, String title, ENotificationStatus status, Date createAt,
            Date updateAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.title = title;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    } 
    
}
