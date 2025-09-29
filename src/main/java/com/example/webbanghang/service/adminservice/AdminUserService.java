package com.example.webbanghang.service.adminservice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.webbanghang.model.entity.User;
import com.example.webbanghang.repository.UserRepository;

@Service
public class AdminUserService {
    private final UserRepository userRepo;
    public AdminUserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    public Page<User> findAllUser(int size, int number) {
        Pageable pageable = PageRequest.of(number, size);
        return userRepo.findByRole_Id(2, pageable);
    }
}
