package com.cc.todolist.service;

import com.cc.todolist.dao.MemoDao;
import com.cc.todolist.entity.MemoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoService {

    @Autowired
    MemoDao memoDao;

    private List<MemoEntity> convert(List<MemoEntity> memos) {
        for (var i : memos) {
            if ((!i.getDerive().isEmpty())) {
                String[] strArray = i.getDerive().substring(1, i.getDerive().length() - 1).split(",");
                int[] intArray = Arrays.stream(strArray).mapToInt(Integer::parseInt).toArray();
                i.setDerive_list(Arrays.stream(intArray).boxed().collect(Collectors.toList()));
            }
        }
        return memos;
    }

    public List<MemoEntity> getMemos(Integer owner) {
        List<MemoEntity> memos = memoDao.getMemos(owner);

        return convert(memos);
    }

    public List<MemoEntity> getMemosByDay(Integer owner, String date) {
        List<MemoEntity> memos =
                memoDao.getMemosByDay(owner, date);
        return convert(memos);
    }

    public void insertMemo(MemoEntity memo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        memo.setDerive(mapper.writeValueAsString(memo.getDerive_list()));
        memoDao.insertMemo(memo);
    }

    public void updateMemo(MemoEntity memo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        memo.setDerive(mapper.writeValueAsString(memo.getDerive_list()));
        memoDao.updateMemo(memo);
    }
}
