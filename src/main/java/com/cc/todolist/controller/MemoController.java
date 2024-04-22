package com.cc.todolist.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cc.todolist.entity.MemoEntity;
import com.cc.todolist.service.MemoService;
import com.cc.todolist.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class MemoController {

    @Autowired
    MemoService memoService;

    @GetMapping("/memo/get")
    public Map<String, Object> getMemos(HttpServletRequest request, @RequestParam String date) {
        Map<String, Object> map = new HashMap<>();

        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtil.verify(token);
        Integer id = Integer.parseInt(verify.getClaim("id").asString());

        map.put("state", true);
        map.put("msg", "请求成功");
        map.put("content", memoService.getMemosByDay(id, date));

        return map;
    }

    @GetMapping("/memo/getall")
    public Map<String, Object> getAllMemos(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtil.verify(token);
        Integer id = Integer.parseInt(verify.getClaim("id").asString());

        map.put("state", true);
        map.put("msg", "请求成功");
        map.put("content", memoService.getMemos(id));

        return map;
    }

    @PostMapping(value = "/memo/insert")
    public Map<String, Object> insertMemo(HttpServletRequest request, @RequestBody MemoEntity memo) {
        Map<String, Object> map = new HashMap<>();

        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtil.verify(token);
        Integer id = Integer.parseInt(verify.getClaim("id").asString());

        memo.setOwner(id);
        List<Integer> derive_list = List.of(0);
        memo.setDerive_list(derive_list);

        try {
            memoService.insertMemo(memo);
            map.put("state", true);
            map.put("msg", "插入成功");
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping(value = "/memo/update")
    public Map<String, Object> updateMemo(HttpServletRequest request, @RequestBody MemoEntity memo) {
        Map<String, Object> map = new HashMap<>();

        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtil.verify(token);
        Integer id = Integer.parseInt(verify.getClaim("id").asString());

        memo.setOwner(id);

        List<MemoEntity> memos = memoService.getMemos(id);
        List<Integer> derive_list = memo.getDerive_list();
        Integer memo_id = memo.getId();
        if (derive_list.size() > 1) {
            Stack<Integer> stk = new Stack<>();
            for (int i = 1; i < derive_list.size(); i++) {
                stk.push(derive_list.get(i));
            }
            while (!stk.empty()) {
                var temp = stk.pop();
                if (Objects.equals(temp, memo_id)) {
                    log.info(String.valueOf(memo_id));
                    map.put("state", false);
                    map.put("msg", "存在循环依赖");
                    return map;
                }
                var obj = memos.stream().filter(m -> Objects.equals(m.getId(), temp)).findFirst();
                if (obj.isPresent()) {
                    var list = obj.get().getDerive_list();
                    if (list.size() > 1) {
                        for (int i = 1; i < list.size(); i++) {
                            stk.push(list.get(i));
                        }
                    }
                }
                else{
                    map.put("state", false);
                    map.put("msg", "不存在指定依赖");
                    return map;
                }
            }
        }

        try {
            memoService.updateMemo(memo);
            map.put("state", true);
            map.put("msg", "更新成功");
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
