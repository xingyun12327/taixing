package com.tixing.owner_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.tixing.owner_management.mapper.AdminMapper;
import com.tixing.owner_management.entity.Admin;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    // 只处理 /login，不处理 /
    @GetMapping("/login")
    public String loginPage(){ return "login"; }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, Model model) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin != null && encoder.matches(password, admin.getPassword())) {
            session.setAttribute("admin", admin);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }

    @GetMapping("/index")
    public String index(){ return "index"; }

    @GetMapping("/logout")
    public String logout(HttpSession session){ session.invalidate(); return "redirect:/login"; }
}