package com.cc.todolist.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/memo/get")
                .addPathPatterns("/user/test")
                .addPathPatterns("/memo/insert")// 其他接口token验证
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/registration");  // 所有用户都放行
    }
}
