package com.ase.gymjudge.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;

@Entity // This tells Hibernate to make a table out of this class
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    private Integer adminID;
    private String description;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    private Date endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany (mappedBy = "competition", cascade= CascadeType.ALL)
    private List<Participants> participants;

    @OneToMany (mappedBy = "competition", cascade= CascadeType.ALL)
    private List<Category> categories;

    @OneToMany (mappedBy = "competition", cascade= CascadeType.ALL)
    private List<Bracket> groups;


    @OneToMany(mappedBy = "competition", cascade= CascadeType.ALL)
    private List<Judge> judges;


    public Integer getId() {
        return id;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Judge> getJudges(){
        return judges;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String startDate) throws ParseException {
        this.startDate = new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
    }

    public void setEndDate(String endDate) throws ParseException {
        this.endDate = new SimpleDateFormat("yyyy-mm-dd").parse(endDate);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public void setJudges(List<Judge> judges){
        this.judges = judges;
    }

    public List<Bracket> getGroups() {
        return groups;
    }

    public void setGroups(List<Bracket> groups) {
        this.groups = groups;
    }
}
