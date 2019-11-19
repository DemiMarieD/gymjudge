package com.ase.gymjudge.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Competition {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    //todo: try to get from login token
    private Integer adminID;
    private String description;
    //todo: try with date instead of string
    private String startDate;
    private String endDate;
    private String type;
    private String status;

    //Setter Methods
    public Integer getId() {
        return id;
    }
    public Integer getAdminID() {
        return adminID;
    }
/*
    @ManyToOne
    private Admin admins;
*/
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public String getStatus() {
        return status;
    }
    public String getDescription() {
        return description;
    }

    //Getter Methods
    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setType(String type) {
        this.type = type;
    }

}
