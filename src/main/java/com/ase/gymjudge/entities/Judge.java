package com.ase.gymjudge.entities;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles;

   @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Apparatus apparatus;


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
   public Apparatus getApparatus(){
       return apparatus;
   }
   public void setApparatus(Apparatus apparatus){
       this.apparatus = apparatus;
   }




}
