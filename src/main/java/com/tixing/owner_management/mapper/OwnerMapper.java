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
    @Results(id = "OwnerResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "idCard", column = "id_card"),
            @Result(property = "isDeleted", column = "is_deleted")
    })
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE is_deleted = 0")
    List<Owner> findAll();

    /**
     * 根据ID查询业主
     */
    @ResultMap("OwnerResultMap")
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE id = #{id} AND is_deleted = 0")
    Owner findById(@Param("id") Integer id);

    /**
     * 根据电话号码查询业主 (用于新增时的重复性检查)
     */
    @ResultMap("OwnerResultMap")
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE phone = #{phone} AND is_deleted = 0")
    Owner findByPhone(@Param("phone") String phone);

    /**
     * 【新增】根据身份证号查询业主 (用于新增时的重复性检查)
     */
    @ResultMap("OwnerResultMap") // 引用已注册的 ResultMap
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE id_card = #{idCard} AND is_deleted = 0")
    Owner findByIdCard(@Param("idCard") String idCard);

    /**
     * 根据电话号码查询业主 (排除指定ID)，用于更新时的重复性检查
     */
    @ResultMap("OwnerResultMap")
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE phone = #{phone} AND id != #{id} AND is_deleted = 0")
    Owner findByPhoneExcludeId(@Param("phone") String phone, @Param("id") Long id);

    /**
     * 新增业主记录
     */
    @Insert("INSERT INTO owner (name, gender, phone, id_card, is_deleted) VALUES (#{name}, #{gender}, #{phone}, #{idCard}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Owner owner);

    /**
     * 逻辑删除业主
     */
    @Update("UPDATE owner SET is_deleted = 1 WHERE id = #{id}")
    int delete(Integer id);

    /**
     * 更新业主信息
     */
    @Update("UPDATE owner SET name = #{name}, gender = #{gender}, phone = #{phone}, id_card = #{idCard} WHERE id = #{id}")
    int update(Owner owner);
}