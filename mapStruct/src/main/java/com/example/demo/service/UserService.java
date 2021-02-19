package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User Sel(int id){
        return userMapper.Sel(id);
    }
}
