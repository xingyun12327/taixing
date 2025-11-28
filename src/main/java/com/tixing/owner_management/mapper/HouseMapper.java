package com.tixing.owner_management.mapper;

import com.tixing.owner_management.entity.House;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface HouseMapper {

    @Select("SELECT * FROM house")
    List<House> findAll();

    @Select("SELECT * FROM house WHERE id = #{id}")
    House findById(Integer id);

    @Insert("INSERT INTO house(building, floor, room_no, area, owner_id, status) " +
            "VALUES(#{building}, #{floor}, #{roomNo}, #{area}, #{ownerId}, #{status})")
    void insert(House house);

    @Update("UPDATE house SET building=#{building}, floor=#{floor}, room_no=#{roomNo}, " +
            "area=#{area}, owner_id=#{ownerId}, status=#{status} WHERE id=#{id}")
    void update(House house);

    @Delete("DELETE FROM house WHERE id=#{id}")
    void delete(Integer id);
}
