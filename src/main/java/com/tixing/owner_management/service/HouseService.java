// service/HouseService.java
package com.tixing.owner_management.service;

import com.tixing.owner_management.entity.House;
import java.util.List;

public interface HouseService {
    List<House> list();
    void add(House house);
    void update(House house);
    void delete(Integer id);
    House findById(Integer id);
}
