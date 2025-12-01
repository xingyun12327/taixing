package com.tixing.owner_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tixing.owner_management.service.OwnerService;
import com.tixing.owner_management.entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/list")
    public String list(Model model) {
        // 确保初次访问时 model 中有 owners 列表
        model.addAttribute("owners", ownerService.list());
        // 确保初次访问时 model 中有一个空的 owner 对象，避免 th:value 报错
        if (!model.containsAttribute("owner")) {
            model.addAttribute("owner", new Owner());
        }
        return "owner_list";
    }

    @PostMapping("/add")
    public String add(Owner owner, Model model) {
        List<String> errors = new ArrayList<>();

        // 1. 格式校验
        // 校验性别
        if (!"男".equals(owner.getGender()) && !"女".equals(owner.getGender())) {
            errors.add("性别校验失败：性别只能选择“男”或者“女”。");
        }
        // 校验电话号码 (11位数字)
        String phone = owner.getPhone();
        if (phone == null || !phone.matches("\\d{11}")) {
            errors.add("电话号码校验失败：电话号码必须为11位数字。");
        }
        // 校验身份证号 (13位)
        String idCard = owner.getIdCard();
        if (idCard == null || idCard.length() != 13) {
            errors.add("身份证号校验失败：身份证号必须为13位。");
        }

        // 2. 业务校验 (仅在格式校验通过后执行)
        if (errors.isEmpty()) {
            try {
                ownerService.add(owner);
                return "redirect:/owner/list"; // 成功，重定向
            } catch (IllegalArgumentException ex) {
                // 捕获 Service 层抛出的业务异常 (如电话号码重复)
                errors.add(ex.getMessage()); // 将业务异常添加到错误列表中
            }
        }

        // 3. 统一错误处理
        if (!errors.isEmpty()) {
            String errorMessage = String.join("\n", errors);

            model.addAttribute("error", errorMessage);
            model.addAttribute("owners", ownerService.list()); // 重新加载列表数据

            // 【优化代码】：将用户提交的 owner 对象返回给页面，用于表单回显
            model.addAttribute("owner", owner);

            return "owner_list"; // 返回列表页并显示所有错误
        }

        return "redirect:/owner/list";
    }

    @PostMapping("/update")
    public String update(Owner owner) {
        ownerService.update(owner);
        return "redirect:/owner/list";
    }

    @GetMapping("/delete")
    public String delete(Integer id) {
        ownerService.delete(id);
        return "redirect:/owner/list";
    }
}