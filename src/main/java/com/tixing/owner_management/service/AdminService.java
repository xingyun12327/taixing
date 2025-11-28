package com.tixing.owner_management.service;

import com.tixing.owner_management.entity.Admin;

public interface AdminService {

    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回 Admin 对象，失败返回 null
     */
    Admin login(String username, String password);

    /**
     * 添加管理员
     */
    void addAdmin(Admin admin);
}
