package com.ase.gymjudge.entities;

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

    @ManyToOne(cascade=CascadeType.ALL)
    private Role role;

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

   @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;

    @Column(name = "apparatus")
    private Apparatus apparatus;

    public Apparatus getApparatus() {
        return apparatus;
    }

    public void setApparatus(Apparatus apparatus) {
        this.apparatus = apparatus;
    }

    public Integer getJudgeID(){
       return judgeID;
   }
   public void setJudgeID(Integer judgeID){
       this.judgeID = judgeID;
   }
   public Role getRole(){
       return role;
   }
   public void setRole(Role role){
       this.role = role;
   }
   public Competition getCompetition(){
       return competition;
   }

   public void setCompetition(Competition competition){
       this.competition = competition;
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

    public void setPassword(String password) {
        this.password = password;
    }
}
