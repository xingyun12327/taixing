package com.tixing.owner_management.mapper;

import com.tixing.owner_management.entity.Owner;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 业主数据访问接口 (MyBatis Mapper)
 */
@Mapper
public interface OwnerMapper {

    /**
     * 获取所有业主列表 (仅查询未删除的记录)
     */
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE is_deleted = 0")
    List<Owner> findAll();

    /**
     * 【新增】根据电话号码查询业主 (用于重复性检查)
     * @param phone 电话号码
     * @return 匹配的业主对象，如果不存在则返回 null
     */
    @Select("SELECT id, name, gender, phone, id_card FROM owner WHERE phone = #{phone} AND is_deleted = 0")
    Owner findByPhone(@Param("phone") String phone);

    /**
     * 新增业主记录
     * @param owner 业主实体对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO owner (name, gender, phone, id_card, is_deleted) VALUES (#{name}, #{gender}, #{phone}, #{idCard}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Owner owner);

    /**
     * 逻辑删除业主 (更新 is_deleted 字段)
     * @param id 业主ID
     * @return 影响的行数
     */
    @Update("UPDATE owner SET is_deleted = 1 WHERE id = #{id}")
    int delete(Integer id);

    /**
     * 更新业主信息
     * @param owner 业主实体对象 (包含ID)
     * @return 影响的行数
     */
    @Update("UPDATE owner SET name = #{name}, gender = #{gender}, phone = #{phone}, id_card = #{idCard} WHERE id = #{id}")
    int update(Owner owner);
}