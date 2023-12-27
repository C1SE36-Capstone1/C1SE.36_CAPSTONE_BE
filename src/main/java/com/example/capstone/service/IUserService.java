package com.example.capstone.service;

import com.example.capstone.dto.UserInfo;
import com.example.capstone.model.User.User;

import java.util.Optional;

public interface IUserService extends IService<User>{
    void update(UserInfo userInfo, Integer id);
    Optional<User> findByEmail(String email);
    void saveUser(UserInfo userInfo);
    Optional<User> findUserDetailByEmail(String email);
}
