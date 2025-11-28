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
     * 更新业主信息
     */
    void update(Owner owner); // <--- 新增的方法
}