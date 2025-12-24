package com.tixing.owner_management.service.impl;

import com.tixing.owner_management.entity.House;
import com.tixing.owner_management.mapper.HouseMapper;
import com.tixing.owner_management.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseMapper houseMapper;

    @Autowired
    public HouseServiceImpl(HouseMapper houseMapper) {
        this.houseMapper = houseMapper;
    }

    @Override
    public List<House> list() {
        return houseMapper.findAll();
    }

    @Override
    public void add(House house) {
        // 1. 业务校验：检查该单元和房号是否已经存在
        // 注意：这里已将 getUnitNum() 改为 getUnit()，getFloorNum() 改为 getFloor()
        House existingHouse = houseMapper.findByUnitAndFloor(house.getUnit(), house.getFloor());

        if (existingHouse != null) {
            // 如果存在，抛出异常阻止插入
            throw new RuntimeException("该房屋信息（" + house.getUnit() + "-" + house.getFloor() + "）已存在，请勿重复添加！");
        }

        // 2. 正常插入
        house.setIsDeleted(0);
        houseMapper.insert(house);
    }

    @Override
    public void delete(Integer id) {
        houseMapper.delete(id);
    }

    @Override
    public House getById(Integer id) {
        return houseMapper.findById(id);
    }

    @Override
    public void update(House house) {
        houseMapper.update(house);
    }
}