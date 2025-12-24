package com.tixing.owner_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 确保这是一个配置类
@Configuration
// 确保Web安全配置被启用
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 定义 BCryptPasswordEncoder Bean。
     * 解决 AdminController 无法注入 encoder 的问题。
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置安全过滤链。
     * 由于我们使用自定义的 AdminController 处理登录，所以放行所有请求。
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF 保护以简化表单提交
                .csrf(csrf -> csrf.disable())
                // 授权所有请求，让自定义 Controller 处理逻辑
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        // 由于所有请求都被 permitAll() 放行，Spring Security 不会介入登录逻辑
        // 您的 AdminController 负责认证和重定向

        return http.build();
    }
}