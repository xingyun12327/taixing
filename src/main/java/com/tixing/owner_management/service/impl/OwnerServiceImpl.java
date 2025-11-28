// service/impl/OwnerServiceImpl.java
package com.tixing.owner_management.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.tixing.owner_management.mapper.OwnerMapper;
import com.tixing.owner_management.entity.Owner;
import com.tixing.owner_management.service.OwnerService;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public List<Owner> list() {
        return ownerMapper.findAll();
    }

    @Override
    public void add(Owner owner) {
        ownerMapper.insert(owner);
    }

    @Override
    public void update(Owner owner) {
        ownerMapper.update(owner);
    }

    @Override
    public void delete(Integer id) {
        ownerMapper.delete(id);
    }

    @Override
    public Owner findById(Integer id) {
        return ownerMapper.findById(id);
    }
}
