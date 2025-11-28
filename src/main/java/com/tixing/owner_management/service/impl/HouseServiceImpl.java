package com.tixing.owner_management.service.impl;

import com.tixing.owner_management.entity.House;
import com.tixing.owner_management.mapper.HouseMapper;
import com.tixing.owner_management.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 房屋业务逻辑实现类
 */
@Service
public class HouseServiceImpl implements HouseService {

    private final HouseMapper houseMapper;

    // 构造器注入 HouseMapper
    @Autowired
    public HouseServiceImpl(HouseMapper houseMapper) {
        this.houseMapper = houseMapper;
    }

    @Override
    public List<House> list() {
        // 调用 Mapper 获取所有房屋（未删除的）
        return houseMapper.findAll();
    }

    @Override
    public void add(House house) {
        // 确保新增时设置 isDeleted 为 0 (默认值)
        house.setIsDeleted(0);
        // 调用 Mapper 插入新的房屋记录
        houseMapper.insert(house);
    }

    @Override
    public void delete(Integer id) {
        // 调用 Mapper 执行逻辑删除
        houseMapper.delete(id);
    }
}