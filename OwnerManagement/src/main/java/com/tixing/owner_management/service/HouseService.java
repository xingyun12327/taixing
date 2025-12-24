package com.tixing.owner_management.service;

import com.tixing.owner_management.entity.House;
import java.util.List;

/**
 * 房屋业务逻辑接口
 */
public interface HouseService {

    /**
     * 获取所有房屋列表
     */
    List<House> list();

    /**
     * 新增房屋
     */
    void add(House house);

    /**
     * 根据ID删除房屋 (逻辑删除)
     */
    void delete(Integer id);

    House getById(Integer id);


    void update(House house);
}