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

    /**
     * 【已修改】处理列表显示和搜索请求
     */
    @GetMapping("/list")
    public String list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "idCard", required = false) String idCard,
            Model model) {

        List<Owner> owners;

        // 检查是否有任何搜索条件被提交
        if ((name != null && !name.isEmpty()) || (phone != null && !phone.isEmpty()) || (idCard != null && !idCard.isEmpty())) {
            // 执行模糊搜索
            owners = ownerService.search(name, phone, idCard);

            // 将搜索条件传回前端用于回显
            Owner searchCriteria = new Owner();
            searchCriteria.setName(name);
            searchCriteria.setPhone(phone);
            searchCriteria.setIdCard(idCard);
            model.addAttribute("searchCriteria", searchCriteria);

        } else {
            // 无搜索条件，返回所有未删除列表
            owners = ownerService.list();
            // 初始化空的搜索条件对象，避免前端 ThymeLeaf 报错
            model.addAttribute("searchCriteria", new Owner());
        }

        model.addAttribute("owners", owners);

        if (!model.containsAttribute("owner")) {
            // 如果不是编辑或新增失败后的回显，则初始化一个空 Owner 对象给新增表单
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

            // 重新获取列表数据（不带搜索条件），以便在错误页面显示完整列表
            model.addAttribute("owners", ownerService.list());

            model.addAttribute("owner", owner); // 表单回显

            // 确保搜索条件对象存在，避免 ThymeLeaf 报错
            model.addAttribute("searchCriteria", new Owner());

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
        // 确保搜索条件对象存在，避免 ThymeLeaf 报错
        model.addAttribute("searchCriteria", new Owner());
        return "owner_list";
    }

    /**
     * 处理 POST /owner/update 请求，更新业主信息
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
                // 理论上只有 phone 会触发，但为了健壮性，保留通用处理。
                errors.add("数据更新失败：违反数据库唯一性约束，请检查输入数据。");
            }
        }

        // 5. 统一错误处理
        if (!errors.isEmpty()) {
            String errorMessage = String.join("\n", errors);

            model.addAttribute("error", errorMessage);
            model.addAttribute("owners", ownerService.list());
            model.addAttribute("owner", submittedOwner); // 表单回显：保留用户提交的 name/phone
            // 确保搜索条件对象存在，避免 ThymeLeaf 报错
            model.addAttribute("searchCriteria", new Owner());

            return "owner_list";
        }

        return "redirect:/owner/list";
    }
}