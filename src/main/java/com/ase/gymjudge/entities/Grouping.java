package com.ase.gymjudge.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Grouping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    // @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;
  
    @OneToMany(mappedBy = "grouping", cascade= CascadeType.ALL)
    private List<Participants> participants;

    // @NotNull
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Apparatus> apparatuses;

    // @NotNull
    @ElementCollection
    // @Column(name = "gymnastIDs")
    private List<Integer> GymnastIDs;

    //todo: check if Order still relevant
    //@ManyToMany(cascade=CascadeType.ALL)
    //private Set<Order> orders;

    // Getter and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Apparatus> getApparatuses() {
        return apparatuses;
    }

    public void setApparatuses(List<Apparatus> apparatuses) {
        this.apparatuses = apparatuses;
    }

    /*public void addApparatus() {
        apparatuses.add(Apparatus.BODEN);
    }*/

    /*public List<Integer> getGymnastIDs() {
        return GymnastIDs;
    }*/

    /*public void setGymnastIDs(List<Integer> gymnastIDs) {
        GymnastIDs = gymnastIDs;
    }*/

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }

    public List<Apparatus> getGroupApparatuses() {
        return competition.getAvailableApparatuses();
    }
}
