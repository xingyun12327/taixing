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
        model.addAttribute("owners", ownerService.list());
        return "owner_list";
    }

    /**
     * 新增业主，并同时校验所有格式要求：
     * 1. 性别只能是 "男" 或 "女"。
     * 2. 电话号码必须是 11 位。
     * 3. 身份证号必须是 13 位。
     * 4. 检查电话号码是否重复 (由 Service 层抛出异常)。
     * 如果有任何错误，都会收集并返回给前端显示。
     */
    @PostMapping("/add")
    public String add(Owner owner, Model model) {
        // 创建一个列表来收集所有格式校验错误信息
        List<String> errors = new ArrayList<>();

        // --- 1. 校验性别 ---
        if (!"男".equals(owner.getGender()) && !"女".equals(owner.getGender())) {
            errors.add("性别校验失败：性别只能选择“男”或者“女”。");
        }

        // --- 2. 校验电话号码 ---
        String phone = owner.getPhone();
        if (phone == null || phone.length() != 11) {
            errors.add("电话号码校验失败：电话号码必须为11位。");
        }

        // --- 3. 校验身份证号 ---
        String idCard = owner.getIdCard();
        if (idCard == null || idCard.length() != 13) {
            errors.add("身份证号校验失败：身份证号必须为13位。");
        }

        // --- 统一处理格式校验结果 ---
        if (!errors.isEmpty()) {
            // 将所有错误信息合并成一个带换行符 (\n) 的字符串，方便前端 <pre> 标签分行显示
            String errorMessage = String.join("\n", errors);

            model.addAttribute("error", errorMessage);

            // 格式校验失败时，重新查询并返回业主列表数据，以便用户在当前页面看到错误提示和列表
            model.addAttribute("owners", ownerService.list());
            return "owner_list";
        }

        // --- 格式校验通过，执行添加并捕获业务异常 (如电话号码重复) ---
        try {
            ownerService.add(owner);
            return "redirect:/owner/list";
        } catch (IllegalArgumentException ex) {
            // 【💥 捕获 Service 层抛出的业务异常，如电话号码重复】
            String errorMessage = ex.getMessage();

            model.addAttribute("error", errorMessage);

            // 业务校验失败时，重新查询并返回业主列表数据，以便用户在当前页面看到错误提示和列表
            model.addAttribute("owners", ownerService.list());
            return "owner_list";
        }
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