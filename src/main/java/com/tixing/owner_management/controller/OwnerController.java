package com.tixing.owner_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tixing.owner_management.service.OwnerService;
import com.tixing.owner_management.entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.dao.DuplicateKeyException;

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
        if (!model.containsAttribute("owner")) {
            model.addAttribute("owner", new Owner());
        }
        return "owner_list";
    }

    /**
     * 处理新增请求
     */
    @PostMapping("/add")
    public String add(Owner owner, Model model) {
        List<String> errors = new ArrayList<>();

        // 1. 格式校验
        if (!"男".equals(owner.getGender()) && !("女".equals(owner.getGender()))) {
            errors.add("性别校验失败：性别只能选择“男”或者“女”。");
        }
        String phone = owner.getPhone();
        if (phone == null || !phone.matches("\\d{11}")) {
            errors.add("电话号码校验失败：电话号码必须为11位数字。");
        }
        String idCard = owner.getIdCard();
        // 假设身份证号是13位数字，如果您的数据库要求是18位，请自行修改
        if (idCard == null || idCard.length() != 13) {
            errors.add("身份证号校验失败：身份证号必须为13位。");
        }

        // 2. 业务校验 (Service 层现在会检查 phone 和 idCard 的唯一性)
        if (errors.isEmpty()) {
            try {
                ownerService.add(owner);
                return "redirect:/owner/list";
            } catch (IllegalArgumentException ex) {
                // 捕获 Service 层抛出的业务异常，如：电话号码已存在、身份证号已存在
                errors.add(ex.getMessage());
            }
        }

        // 3. 统一错误处理
        if (!errors.isEmpty()) {
            String errorMessage = String.join("\n", errors);

            model.addAttribute("error", errorMessage);
            model.addAttribute("owners", ownerService.list());
            model.addAttribute("owner", owner); // 表单回显

            return "owner_list";
        }

        return "redirect:/owner/list";
    }

    @PostMapping("/delete")
    public String deleteById(@RequestParam Integer id) {
        ownerService.delete(id);
        return "redirect:/owner/list";
    }

    @GetMapping("/edit")
    public String editOwner(@RequestParam Integer id, Model model) {
        Owner owner = ownerService.findById(id);
        if (owner == null) {
            model.addAttribute("error", "业主ID不存在或已被删除。");
            return "redirect:/owner/list";
        }
        model.addAttribute("owners", ownerService.list());
        model.addAttribute("owner", owner);
        return "owner_list";
    }

    /**
     * 处理 POST /owner/update 请求，更新业主信息
     * 【核心逻辑】：强制只允许修改 name 和 phone
     */
    @PostMapping("/update")
    public String update(Owner submittedOwner, Model model) {
        List<String> errors = new ArrayList<>();

        // 1. 基础校验：ID 必须存在
        if (submittedOwner.getId() == null || submittedOwner.getId() <= 0) {
            errors.add("更新失败：业主ID不能为空或无效。");
        }

        // 2. 强制业务逻辑：只允许修改 Name 和 Phone，查找原始数据并覆盖
        Owner originalOwner = null;
        if (errors.isEmpty()) {
            originalOwner = ownerService.findById(submittedOwner.getId().intValue());
            if (originalOwner == null) {
                errors.add("更新失败：业主ID " + submittedOwner.getId() + " 不存在或已被删除。");
            } else {
                // 【核心逻辑】：用原始数据覆盖提交的 Gender 和 IDCard
                submittedOwner.setGender(originalOwner.getGender());
                submittedOwner.setIdCard(originalOwner.getIdCard());
            }
        }

        // 3. 对允许修改的字段进行格式校验 (只需校验 Name 和 Phone)
        if (submittedOwner.getName() == null || submittedOwner.getName().trim().isEmpty()) {
            errors.add("姓名校验失败：姓名不能为空。");
        }
        String phone = submittedOwner.getPhone();
        if (phone == null || !phone.matches("\\d{11}")) {
            errors.add("电话号码校验失败：电话号码必须为11位数字。");
        }

        // 4. 业务校验 & 数据库操作
        if (errors.isEmpty()) {
            try {
                ownerService.update(submittedOwner); // 提交带有原始 gender/idCard 的对象
                return "redirect:/owner/list"; // 成功，重定向
            } catch (IllegalArgumentException ex) {
                // 捕获 Service 层抛出的业务异常 (如电话号码已被占用)
                errors.add(ex.getMessage());
            } catch (DuplicateKeyException ex) {
                // 捕获数据库唯一约束异常
                String errorMessage = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";

                // 此时只可能是 phone 或 idCard 约束被触发，但由于 idCard 已经被强制为原始值，
                // 理论上只有 phone 会触发，但为了健壮性，保留通用处理。
                if (errorMessage.contains("phone")) {
                    errors.add("电话号码校验失败：您提交的电话号码已被其他业主使用。");
                } else {
                    errors.add("数据更新失败：违反数据库唯一性约束，请检查输入数据。");
                }
            }
        }

        // 5. 统一错误处理
        if (!errors.isEmpty()) {
            String errorMessage = String.join("\n", errors);

            model.addAttribute("error", errorMessage);
            model.addAttribute("owners", ownerService.list());
            model.addAttribute("owner", submittedOwner); // 表单回显：保留用户提交的 name/phone

            return "owner_list";
        }

        return "redirect:/owner/list";
    }
}