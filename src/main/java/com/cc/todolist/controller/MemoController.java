package com.cc.todolist.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cc.todolist.service.MemoService;
import com.cc.todolist.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class MemoController {

    @Autowired
    MemoService memoService;

    @GetMapping("/memo/get")
    public Map<String, Object> registration(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtil.verify(token);
        Integer id = Integer.parseInt(verify.getClaim("id").asString());

        map.put("state", true);
        map.put("msg", "请求成功");
        map.put("content", memoService.getOwners(id));

        return map;
    }
}
