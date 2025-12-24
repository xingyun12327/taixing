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
     * 【新增】根据姓名、电话或身份证号进行模糊搜索
     */
    List<Owner> search(String name, String phone, String idCard);

    /**
     * 根据ID查询业主信息
     */
    Owner findById(Integer id);

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
    void update(Owner owner);
}