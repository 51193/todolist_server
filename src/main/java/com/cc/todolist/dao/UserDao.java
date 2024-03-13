package com.cc.todolist.dao;

import com.cc.todolist.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    UserEntity login(UserEntity user);
    void registration(UserEntity user);
}
