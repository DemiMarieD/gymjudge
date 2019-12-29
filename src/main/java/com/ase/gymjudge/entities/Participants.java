package com.ase.gymjudge.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @NotNull
    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    private Date birthday;

    @ManyToOne (fetch = FetchType.LAZY)
    private Competition competition;

    @ManyToOne (fetch = FetchType.LAZY) //todo check what to do when deleting
    private Category category;

    //todo check how to.
    @ManyToOne (cascade= CascadeType.ALL)
    private Grouping grouping;


    public String getParticipantsInfo(){
        String output = "";
        output += firstname;
        output += ", ";
        output += lastname;
        output += "  ";
        output += "(" + getAge() + ")";
        output += "  ";
        output += gender.getDisplayValue();
        return output;
    }

    public int getAge(){
        Date today = new Date();
        long differenceInSeconds = TimeUnit.MILLISECONDS.toSeconds(today.getTime() - birthday.getTime());
        return (int) differenceInSeconds/31536000;
    }

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

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }
}
