// service/impl/HouseServiceImpl.java
package com.tixing.owner_management.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.tixing.owner_management.mapper.HouseMapper;
import com.tixing.owner_management.entity.House;
import com.tixing.owner_management.service.HouseService;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public List<House> list() {
        return houseMapper.findAll();
    }

    @Override
    public void add(House house) {
        houseMapper.insert(house);
    }

    @Override
    public void update(House house) {
        houseMapper.update(house);
    }

    @Override
    public void delete(Integer id) {
        houseMapper.delete(id);
    }

    @Override
    public House findById(Integer id) {
        return houseMapper.findById(id);
    }
}
