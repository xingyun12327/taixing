package com.tixing.owner_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 根路径控制器，用于处理应用首页访问和登录成功后的跳转。
 */
@Controller
public class RootController {

    /**
     * 处理根路径请求 (http://localhost:8080/)。
     * 重定向到业主列表页面。
     */
    @GetMapping("/")
    public String redirectToOwnerList() {
        return "redirect:/owner/list";
    }
}