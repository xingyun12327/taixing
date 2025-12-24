package com.tixing.owner_management.entity;

import lombok.Data;

/**
 * 房屋实体类
 * 使用 Lombok 的 @Data 注解自动生成 getter, setter, toString, equals, hashCode等方法
 */
@Data
public class House {

    // 房屋ID (主键)
    private Integer id;

    // 单元号 (例如: 1栋)
    private String unit;

    // 楼层/房号 (例如: 101)
    private String floor;

    // 房屋状态 (例如: 未入住, 已入住, 待租)
    private String status;

    // 软删除标记 (0:未删除, 1:已删除) - 遵循之前的讨论，推荐在表中添加此字段
    private Integer isDeleted;

    // 新增：业主姓名
    private String ownerName;
}