package com.tixing.owner_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    /**
     * 修改点：将根路径 "/" 重定向到 "/index" (仪表盘主页)
     * 而不是 "/owner/list"
     */
    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }
}