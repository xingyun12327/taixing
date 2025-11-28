package com.tixing.owner_management.entity;
import lombok.Data;
@Data
public class House {
    private Integer id;
    private String building;
    private Integer floor;
    private String roomNo;
    private Double area;
    private Integer ownerId;
    private String status;
}
