package com.tixing.owner_management.entity;
import lombok.Data;
@Data
public class Owner {
    private Integer id;
    private String name;
    private String gender;
    private String phone;
    private String idCard;
    private Integer isDeleted;
}
