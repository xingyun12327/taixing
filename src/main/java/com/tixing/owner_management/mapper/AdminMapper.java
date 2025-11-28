package com.tixing.owner_management.mapper;

import com.tixing.owner_management.entity.Admin;
import org.apache.ibatis.annotations.Insert; // 确保导入
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select; // 确保导入

@Mapper
public interface AdminMapper {

    // ❗ 解决方案：添加 @Select 注解
    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin findByUsername(@Param("username") String username);

    // ❗ 解决方案：添加 @Insert 注解
    @Insert("INSERT INTO admin(username, password) VALUES(#{username}, #{password})")
    void insert(Admin admin);
}