package com.cc.todolist.service;

import com.cc.todolist.dao.TestDao;
import com.cc.todolist.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestDao testDao ;

    public TestEntity getById(Integer id){
        return testDao.getById(id);
    }
}