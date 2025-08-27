package com.example.webbanghang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webbanghang.model.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    public List<Notification> findByUser_Id(long userId);
}
