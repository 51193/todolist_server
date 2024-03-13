package com.cc.todolist.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cc.todolist.entity.UserEntity;
import com.cc.todolist.service.UserService;
import com.cc.todolist.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/registration")
    public Map<String, Object> registration(UserEntity user) {
        log.info("新注册用户名：[{}]", user.getName());
        log.info("新注册密码：[{}]", user.getPassword());
        Map<String, Object> map = new HashMap<>();
        try {
            userService.registration(user);
            map.put("state", true);
            map.put("msg", "注册成功");
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/user/login")
    public Map<String, Object> login(@RequestBody UserEntity user) {
        log.info("用户名：[{}]", user.getName());
        log.info("密码：[{}]", user.getPassword());
        Map<String, Object> map = new HashMap<>();
        try {
            UserEntity userEntity = userService.login(user);
            Map<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(userEntity.getId()));
            payload.put("name", userEntity.getName());
            payload.put("authority", userEntity.getAuthority());
            // 生成jwt令牌
            String token = JWTUtil.getToken(payload);
            map.put("state", true);
            map.put("msg", "认证成功！");
            map.put("token", token);  // 响应token
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/user/check")
    public Map<String, Object> check(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        String token = request.getHeader("token");

        if (token == null || token.isEmpty()) {
            map.put("state", false);
        }
        try {
            // 验证令牌
            JWTUtil.verify(token);
            map.put("state", true);
        } catch (Exception e) {
            map.put("state", false);
        }
        return map;
    }

    @PostMapping("/user/test")
    public Map<String, Object> test(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        // 验证令牌  交给拦截器去做
        // 只需要在这里处理自己的业务逻辑
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtil.verify(token);
        map.put("state", true);
        map.put("msg", "请求成功");
        return map;
    }
}
