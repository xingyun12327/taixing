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
    @Transactional
    public void add(Owner owner) {
        // 1. 获取身份证和电话对应的现有记录（无论是否已删除）
        Owner existingByIdCard = ownerMapper.findByIdCard(owner.getIdCard());
        Owner existingByPhone = ownerMapper.findByPhoneAll(owner.getPhone());

        // 2. 预校验：如果电话被“活跃业主”占用，必须报错
        if (existingByPhone != null && existingByPhone.getIsDeleted() == 0) {
            if (existingByIdCard == null || !existingByPhone.getId().equals(existingByIdCard.getId())) {
                throw new IllegalArgumentException("电话号码 " + owner.getPhone() + " 已被其他活跃业主占用。");
            }
        }

        // 3. 预校验：如果身份证号被“活跃业主”占用，必须报错
        if (existingByIdCard != null && existingByIdCard.getIsDeleted() == 0) {
            throw new IllegalArgumentException("身份证号 " + owner.getIdCard() + " 已存在。");
        }

        // 4. 【方案二核心逻辑】：自动物理删除冲突的“僵尸”数据
        // 如果新电话被另一条【已删除】的记录占用，直接彻底删除那条记录腾出位置
        if (existingByPhone != null && existingByPhone.getIsDeleted() == 1) {
            if (existingByIdCard == null || !existingByPhone.getId().equals(existingByIdCard.getId())) {
                ownerMapper.physicalDelete(existingByPhone.getId().intValue());
            }
        }

        // 5. 执行恢复或新增
        if (existingByIdCard != null && existingByIdCard.getIsDeleted() == 1) {
            // 身份证号匹配，恢复原有记录 ID 并激活
            owner.setId(existingByIdCard.getId());
            owner.setIsDeleted(0);
            ownerMapper.restore(owner);
        } else {
            // 全新记录
            owner.setIsDeleted(0);
            ownerMapper.insert(owner);
        }
    }

    @Override
    public List<Owner> list() { return ownerMapper.findAll(); }
    @Override
    public List<Owner> search(String name, String phone, String idCard) { return ownerMapper.findByCriteria(name, phone, idCard); }
    @Override
    public Owner findById(Integer id) { return ownerMapper.findById(id); }
    @Override
    @Transactional
    public void delete(Integer id) { ownerMapper.delete(id); }

    @Override
    @Transactional
    public void update(Owner owner) {
        Owner existingOwner = ownerMapper.findByPhoneExcludeId(owner.getPhone(), owner.getId().longValue());
        if (existingOwner != null) {
            throw new IllegalArgumentException("电话号码 " + owner.getPhone() + " 已被其他业主使用。");
        }
        ownerMapper.update(owner);
    }
}