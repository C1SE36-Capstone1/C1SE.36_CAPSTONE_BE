package com.example.capstone.service;

import com.example.capstone.dto.UserInfo;
import com.example.capstone.model.User.User;

public interface IUserService extends IService<User>{
    void update(UserInfo userInfo, Integer id);

    void saveUser(UserInfo userInfo);
}
