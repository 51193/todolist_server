package com.cc.todolist.service;

import com.cc.todolist.dao.UserDao;
import com.cc.todolist.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public UserEntity login(UserEntity user) {
        UserEntity userEntity = userDao.login(user);
        if (userEntity != null) {
            return userEntity;
        } else {
            throw new RuntimeException("登录失败");
        }
    }

    public void registration(UserEntity user) {
        userDao.registration(user);
    }
}
