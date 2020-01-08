package com.ase.gymjudge.entities;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "club")
    private String club;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private int active;

    //for Judges
    @Column(name = "apparatus")
    private Apparatus apparatus;
    public Apparatus getApparatus() {
        return apparatus;
    }
    public void setApparatus(Apparatus apparatus) {
        this.apparatus = apparatus;
    }

    @Column(name = "judgePassword")
    private String judgePassword;
    public void setJudgePassword(String judgePassword) {
        this.judgePassword = judgePassword;
    }
    public String getJudgePassword() {
        return judgePassword;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;
    public Competition getCompetition() {
        return competition;
    }
    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    //todo use maybe for deleting later
    //caused error on first try "can not be set to null"; also problem: connected to role can not be deleted
   /* @Column(name = "competition")
    private int competitionId;
    public int getCompetitionId() {
        return competitionId;
    }
    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }*/

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}