package com.tixing.owner_management.controller;

import com.tixing.owner_management.entity.House;
import com.tixing.owner_management.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 房屋管理控制器
 * 负责处理房屋信息的列表展示、新增和删除请求。
 */
@Controller
@RequestMapping("/house")
public class HouseController {

    private final HouseService houseService;

    // 构造器注入 HouseService
    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    /**
     * 显示房屋列表页面
     * URL: /house/list
     * @param model Spring MVC的模型对象，用于向视图传递数据
     * @return 房屋列表视图名称 (house_list.html)
     */
    @GetMapping("/list")
    public String list(Model model) {
        // 调用 Service 层获取所有房屋列表
        List<House> houses = houseService.list();
        // 将房屋列表添加到模型中，供 Thymeleaf 渲染
        model.addAttribute("houses", houses);
        return "house_list"; // 对应 src/main/resources/templates/house_list.html
    }

    /**
     * 处理新增房屋请求
     * URL: /house/add (POST)
     * @param house 房屋实体对象，通过表单绑定接收数据
     * @return 重定向到房屋列表页面
     */
    @PostMapping("/add")
    public String add(House house) {
        // 调用 Service 层保存房屋信息
        houseService.add(house);
        // 新增成功后，重定向到列表页面以显示最新数据
        return "redirect:/house/list";
    }

    /**
     * 处理删除房屋请求
     * URL: /house/delete?id=... (GET)
     * @param id 要删除房屋的ID
     * @return 重定向到房屋列表页面
     */
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        // 调用 Service 层执行删除操作
        houseService.delete(id);
        // 删除成功后，重定向到列表页面
        return "redirect:/house/list";
    }
}