package com.tixing.owner_management.service.impl;

import com.tixing.owner_management.entity.Admin;
import com.tixing.owner_management.mapper.AdminMapper;
import com.tixing.owner_management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {
        // 通过用户名查询数据库
        Admin admin = adminMapper.findByUsername(username);

        // 如果存在且密码匹配，返回 admin；否则返回 null
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }

    @Override
    public void addAdmin(Admin admin) {
        // 插入数据库
        adminMapper.insert(admin);
    }
}
