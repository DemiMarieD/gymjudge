package com.ase.gymjudge.entities;
import org.hibernate.annotations.Cascade;

import java.util.Set;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;

    private String club;

    private String firstname;

    private String lastname;

    private String password;

    private int active;

    //for Judges
    private Apparatus apparatus;
    public Apparatus getApparatus() {
        return apparatus;
    }
    public void setApparatus(Apparatus apparatus) {
        this.apparatus = apparatus;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;
    public Competition getCompetition() {
        return competition;
    }
    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    @ManyToOne(cascade=CascadeType.ALL)
    //@JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private Role roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }
}