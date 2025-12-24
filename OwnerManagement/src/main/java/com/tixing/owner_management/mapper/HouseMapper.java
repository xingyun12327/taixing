package com.tixing.owner_management.mapper;

import com.tixing.owner_management.entity.House;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface HouseMapper {

    // 1. 查询所有未删除房屋
    @Select("SELECT id, unit, floor, status, owner_name AS ownerName FROM house WHERE is_deleted = 0")
    List<House> findAll();

    // 2. 根据单元和房号查找（解决报错 line 29）
    @Select("SELECT id, unit, floor, status, owner_name AS ownerName FROM house WHERE unit = #{unit} AND floor = #{floor} AND is_deleted = 0 LIMIT 1")
    House findByUnitAndFloor(@Param("unit") String unit, @Param("floor") String floor);

    // 3. 根据ID查询单个房屋（用于编辑回显）
    @Select("SELECT id, unit, floor, status, owner_name AS ownerName FROM house WHERE id = #{id} AND is_deleted = 0")
    House findById(Integer id);

    // 4. 插入新房屋
    @Insert("INSERT INTO house (unit, floor, status, owner_name, is_deleted) VALUES (#{unit}, #{floor}, #{status}, #{ownerName}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(House house);

    // 5. 更新业主姓名和状态（编辑功能）
    @Update("UPDATE house SET owner_name = #{ownerName}, status = #{status} WHERE id = #{id}")
    int update(House house);

    // 6. 逻辑删除房屋（解决报错 line 43）
    @Update("UPDATE house SET is_deleted = 1 WHERE id = #{id}")
    int delete(Integer id);
}