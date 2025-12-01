package com.tixing.owner_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
// Import other necessary annotations like @GeneratedValue, etc.

@Entity // Assuming this is a JPA entity
public class Owner {

    @Id
    private Long id; // Example primary key

    private String name;

    private String gender;

    // --- 新增字段 (根据模板和表单需求添加) ---
    private String phone;
    private String idCard;
    // ------------------------------------

    // --- 新增软删除字段 ---
    private int isDeleted; // 软删除标志：0 表示未删除，1 表示已删除
    // ----------------------

    public Owner() {
    }

    // 标准构造函数 (用于初始化所有字段)
    public Owner(Long id, String name, String gender, String phone, String idCard, int isDeleted) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.idCard = idCard;
        this.isDeleted = isDeleted; // 添加 isDeleted 字段初始化
    }

    // --- Getter and Setter for 'id' and 'name' ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    /**
     * @return The phone number of the owner.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The new phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // --- Getter and Setter for 'idCard' ---

    /**
     * @return The ID card number of the owner.
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard The new ID card number.
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    // --- 新增 Getter and Setter for 'isDeleted' (软删除标志) ---

    /**
     * @return The deletion status (0=Not Deleted, 1=Deleted).
     */
    public int getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted The deletion status to set.
     */
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}