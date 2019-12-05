package com.ase.gymjudge.entities;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "judge")
public class Judge {
    //#depending on type of competitions
    //is connected to competition
    //todo: think about other tables judge can be connected with
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer judgeID;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles;

   @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;


   public Integer getJudgeID(){
       return judgeID;
   }
   public void setJudgeID(Integer judgeID){
       this.judgeID = judgeID;
   }
   public String getEmail(){
       return email;
   }
   public void setEmail(String email){
       this.email = email;
   }
   public String getFirstname(){
       return firstname;
   }
   public void setFirstname(String firstname){
       this.firstname = firstname;
   }
   public String getLastname(){
       return lastname;
   }
   public void setLastname(String lastname){
       this.lastname = lastname;
   }
   public String getPassword(){
       return password;
   }
   public void setPassword(String password){
       this.password = password;
   }
   public Set<Role> getRoles(){
       return roles;
   }
   public void setRoles(Set<Role> roles){
       this.roles = roles;
   }
   public Competition getCompetition(){
       return competition;
   }

   public void setCompetition(Competition competition){
       this.competition = competition;
   }



}
