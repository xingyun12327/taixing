package com.tixing.owner_management.controller;

import com.tixing.owner_management.entity.House;
import com.tixing.owner_management.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 新增

@Controller
@RequestMapping("/house")
public class HouseController {

    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("houses", houseService.list());
        return "house_list";
    }

    @PostMapping("/add")
    public String add(House house, RedirectAttributes redirectAttributes) {
        try {
            houseService.add(house);
        } catch (RuntimeException e) {
            // 如果重复添加，捕获 Service 抛出的异常，并将错误消息传回前端
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/house/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        houseService.delete(id);
        return "redirect:/house/list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") Integer id, Model model) {
        House house = houseService.getById(id);
        model.addAttribute("house", house);
        return "house_edit"; // 返回编辑页面模板
    }

    // 执行更新操作
    @PostMapping("/update")
    public String update(House house) {
        houseService.update(house);
        return "redirect:/house/list";
    }
}