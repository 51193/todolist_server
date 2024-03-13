package com.cc.todolist.dao;

import com.cc.todolist.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDao {

    TestEntity getById(Integer id);

}