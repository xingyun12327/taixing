package com.tixing.owner_management.service;

import com.tixing.owner_management.entity.Owner;
import java.util.List;

/**
 * 业主业务逻辑接口
 */
public interface OwnerService {

    /**
     * 获取所有业主列表
     */
    List<Owner> list();

    /**
     * 新增业主
     */
    void add(Owner owner);

    /**
     * 根据ID删除业主 (逻辑删除)
     */
    void delete(Integer id);

    /**
     * 根据身份证号查询业主 (新增)
     */
    Owner findByIdCard(String idCard); // <--- 新增方法声明

    /**
     * 更新业主信息
     */
    void update(Owner owner);
}