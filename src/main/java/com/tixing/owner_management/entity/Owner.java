package com.tixing.owner_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
// 如果使用 JPA/Hibernate，可能还需要 @GeneratedValue 等注解
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity // 假设这是 JPA 实体
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 假设使用数据库自增ID
    private Long id;

    private String name;

    private String gender;

    private String phone;
    private String idCard;

    private int isDeleted; // 软删除标志：0 表示未删除，1 表示已删除

    public Owner() {
    }

    // 标准构造函数
    public Owner(Long id, String name, String gender, String phone, String idCard, int isDeleted) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.idCard = idCard;
        this.isDeleted = isDeleted;
    }

    // --- Getter and Setter for 'id' ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // --- Getter and Setter for 'name' ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // --- Getter and Setter for 'gender' ---

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // --- Getter and Setter for 'phone' ---

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // --- Getter and Setter for 'idCard' ---

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    // --- Getter and Setter for 'isDeleted' ---

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}