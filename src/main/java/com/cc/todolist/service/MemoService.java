package com.cc.todolist.service;

import com.cc.todolist.dao.MemoDao;
import com.cc.todolist.dao.UserDao;
import com.cc.todolist.entity.MemoEntity;
import com.cc.todolist.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoService {

    @Autowired
    MemoDao memoDao;

    public List<MemoEntity> getOwners(Integer owner) {
        return memoDao.getOwners(owner);
    }
}
