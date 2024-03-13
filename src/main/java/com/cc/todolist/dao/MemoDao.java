package com.cc.todolist.dao;

import com.cc.todolist.entity.MemoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MemoDao {
    List<MemoEntity> getOwners(Integer owner);
}
