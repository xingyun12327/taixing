package com.tixing.owner_management.service.impl;

import com.tixing.owner_management.entity.Owner;
import com.tixing.owner_management.mapper.OwnerMapper;
import com.tixing.owner_management.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Owner findById(Integer id) {
        return ownerMapper.findById(id);
    }

    @Override
    @Transactional
    public void add(Owner owner) {
        // 1. 检查电话号码是否重复
        Owner existingOwnerByPhone = ownerMapper.findByPhone(owner.getPhone());
        if (existingOwnerByPhone != null) {
            throw new IllegalArgumentException("电话号码 " + owner.getPhone() + " 已存在，请勿重复添加。");
        }

        // 2. 【核心修复】检查身份证号是否重复
        Owner existingOwnerByIdCard = ownerMapper.findByIdCard(owner.getIdCard());
        if (existingOwnerByIdCard != null) {
            throw new IllegalArgumentException("身份证号 " + owner.getIdCard() + " 已存在，请勿重复添加。");
        }

        owner.setIsDeleted(0);
        ownerMapper.insert(owner);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ownerMapper.delete(id);
    }

    @Override
    @Transactional
    public void update(Owner owner) {
        // 1. 检查电话号码是否被其他业主占用 (排除当前 ID)
        Owner existingOwner = ownerMapper.findByPhoneExcludeId(owner.getPhone(), owner.getId());

        if (existingOwner != null) {
            throw new IllegalArgumentException("电话号码 " + owner.getPhone() + " 已被其他业主使用，请更换。");
        }

        // 2. 执行更新操作
        int rows = ownerMapper.update(owner);

        if (rows == 0 && ownerMapper.findById(owner.getId().intValue()) == null) {
            throw new IllegalArgumentException("更新失败：业主ID " + owner.getId() + " 不存在。");
        }
    }
}