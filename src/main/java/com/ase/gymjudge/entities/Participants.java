package com.ase.gymjudge.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String club;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    private Date birthday;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Grouping grouping;


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getClub() {
        return club;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setGrouping(Grouping grouping) {
        this.grouping = grouping;
    }

    public Grouping getGrouping() {
        return grouping;
    }
}
