package com.tixing.owner_management.mapper;

import com.tixing.owner_management.entity.House;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 房屋数据访问接口 (MyBatis Mapper)
 */
@Mapper
public interface HouseMapper {

    /**
     * 获取所有房屋列表 (仅查询未删除的记录)
     */
    @Select("SELECT id, unit, floor, status FROM house WHERE is_deleted = 0")
    List<House> findAll();

    /**
     * 新增房屋记录
     * @param house 房屋实体对象，包含 unit, floor, status
     * @return 影响的行数
     */
    @Insert("INSERT INTO house (unit, floor, status, is_deleted) VALUES (#{unit}, #{floor}, #{status}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(House house);

    /**
     * 逻辑删除房屋 (更新 is_deleted 字段)
     * @param id 房屋ID
     * @return 影响的行数
     */
    @Update("UPDATE house SET is_deleted = 1 WHERE id = #{id}")
    int delete(Integer id);
}