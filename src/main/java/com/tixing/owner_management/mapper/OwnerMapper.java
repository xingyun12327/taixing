package com.tixing.owner_management.mapper;

import com.tixing.owner_management.entity.Owner;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface OwnerMapper {

    @Select("SELECT * FROM owner WHERE is_deleted = 0")
    List<Owner> findAll();

    @Select("SELECT * FROM owner WHERE id = #{id}")
    Owner findById(Integer id);

    @Insert("INSERT INTO owner(name, gender, phone, id_card, is_deleted) " +
            "VALUES(#{name}, #{gender}, #{phone}, #{idCard}, 0)")
    void insert(Owner owner);

    @Update("UPDATE owner SET name=#{name}, gender=#{gender}, phone=#{phone}, id_card=#{idCard} " +
            "WHERE id=#{id}")
    void update(Owner owner);

    @Update("UPDATE owner SET is_deleted=1 WHERE id=#{id}")
    void delete(Integer id);
}
