package com.ase.gymjudge.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity // This tells Hibernate to make a table out of this class
public class Admin {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String firstName;
    private String lastName;
    private String club;
    private String login;
    private String password;

    //Setter Methods
    public Integer getId() {
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getClub() {
        return club;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }

    //Getter Methods
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setClub(String club) {
        this.club = club;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
