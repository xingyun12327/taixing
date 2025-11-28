package com.tixing.owner_management.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tixing.owner_management.service.OwnerService;
import com.tixing.owner_management.entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

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

    @PostMapping("/add")
    public String add(Owner owner) {
        ownerService.add(owner);
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
