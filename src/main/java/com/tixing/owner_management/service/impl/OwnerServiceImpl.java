package com.tixing.owner_management.service.impl;

import com.tixing.owner_management.entity.Owner;
import com.tixing.owner_management.mapper.OwnerMapper;
import com.tixing.owner_management.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerMapper ownerMapper;

    @Autowired
    public OwnerServiceImpl(OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

    @Override
    public List<Owner> list() {
        return ownerMapper.findAll();
    }

    @Override
    public void add(Owner owner) {
        // 1. 检查电话号码是否重复
        Owner existingOwnerByPhone = ownerMapper.findByPhone(owner.getPhone());

        if (existingOwnerByPhone != null) {
            throw new IllegalArgumentException("电话号码 " + owner.getPhone() + " 已存在，请勿重复添加。");
        }

        // 2. ✨ 关键修改：检查身份证号是否重复
        Owner existingOwnerByIdCard = ownerMapper.findByIdCard(owner.getIdCard());
        if (existingOwnerByIdCard != null) {
            throw new IllegalArgumentException("身份证号 " + owner.getIdCard() + " 已存在，请勿重复添加。");
        }

        owner.setIsDeleted(0);
        ownerMapper.insert(owner);
    }

    @Override
    public void delete(Integer id) {
        ownerMapper.delete(id);
    }

    @Override
    public void update(Owner owner) {
        ownerMapper.update(owner);
    }

    // 【新增】实现 findByIdCard 方法
    @Override
    public Owner findByIdCard(String idCard) {
        return ownerMapper.findByIdCard(idCard);
    }
}