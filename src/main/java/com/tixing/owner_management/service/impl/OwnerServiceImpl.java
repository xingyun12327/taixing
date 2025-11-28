package com.tixing.owner_management.service.impl;

import com.tixing.owner_management.entity.Owner;
import com.tixing.owner_management.mapper.OwnerMapper;
import com.tixing.owner_management.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 业主业务逻辑实现类
 * 负责实现 OwnerService 接口中定义的各项业务操作。
 */
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerMapper ownerMapper;

    // 构造器注入 OwnerMapper
    @Autowired
    public OwnerServiceImpl(OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

    @Override
    public List<Owner> list() {
        // 调用 Mapper 获取所有业主（未删除的）
        return ownerMapper.findAll();
    }

    @Override
    public void add(Owner owner) {
        // 业务逻辑：确保新增时设置 isDeleted 为 0 (未删除)
        owner.setIsDeleted(0);
        // 调用 Mapper 插入新的业主记录
        ownerMapper.insert(owner);
    }

    @Override
    public void delete(Integer id) {
        // 调用 Mapper 执行逻辑删除 (更新 is_deleted 字段)
        ownerMapper.delete(id);
    }

    @Override
    public void update(Owner owner) {
        // 调用 Mapper 更新业主记录
        ownerMapper.update(owner);
    }
}