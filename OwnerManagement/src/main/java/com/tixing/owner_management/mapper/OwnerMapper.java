package com.tixing.owner_management.mapper;

import com.tixing.owner_management.entity.Owner;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface OwnerMapper {

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

    @ResultMap("OwnerResultMap")
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE id_card = #{idCard} LIMIT 1")
    Owner findByIdCard(@Param("idCard") String idCard);

    @ResultMap("OwnerResultMap")
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE phone = #{phone} LIMIT 1")
    Owner findByPhoneAll(@Param("phone") String phone);

    @Update("UPDATE owner SET name = #{name}, gender = #{gender}, phone = #{phone}, " +
            "id_card = #{idCard}, is_deleted = 0 WHERE id = #{id}")
    int restore(Owner owner);

    // 方案二关键：物理删除指定 ID 的记录
    @Delete("DELETE FROM owner WHERE id = #{id}")
    int physicalDelete(@Param("id") Integer id);

    @Insert("INSERT INTO owner (name, gender, phone, id_card, is_deleted) VALUES (#{name}, #{gender}, #{phone}, #{idCard}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Owner owner);

    @Update("UPDATE owner SET is_deleted = 1 WHERE id = #{id}")
    int delete(Integer id);

    @Update("UPDATE owner SET name = #{name}, gender = #{gender}, phone = #{phone}, id_card = #{idCard} WHERE id = #{id}")
    int update(Owner owner);

    @ResultMap("OwnerResultMap")
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE id = #{id} AND is_deleted = 0")
    Owner findById(@Param("id") Integer id);

    @ResultMap("OwnerResultMap")
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE phone = #{phone} AND is_deleted = 0")
    Owner findByPhone(@Param("phone") String phone);

    @ResultMap("OwnerResultMap")
    @Select("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE phone = #{phone} AND id != #{id} AND is_deleted = 0")
    Owner findByPhoneExcludeId(@Param("phone") String phone, @Param("id") Long id);

    @ResultMap("OwnerResultMap")
    @SelectProvider(type = OwnerSqlProvider.class, method = "findByCriteria")
    List<Owner> findByCriteria(@Param("name") String name, @Param("phone") String phone, @Param("idCard") String idCard);

    class OwnerSqlProvider {
        public String findByCriteria(@Param("name") String name, @Param("phone") String phone, @Param("idCard") String idCard) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id, name, gender, phone, id_card, is_deleted FROM owner WHERE is_deleted = 0");
            if (name != null && !name.isEmpty()) sql.append(" AND name LIKE CONCAT('%', #{name}, '%')");
            if (phone != null && !phone.isEmpty()) sql.append(" AND phone LIKE CONCAT('%', #{phone}, '%')");
            if (idCard != null && !idCard.isEmpty()) sql.append(" AND id_card LIKE CONCAT('%', #{idCard}, '%')");
            return sql.toString();
        }
    }
}