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

    @ManyToOne(cascade = CascadeType.ALL)
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

    //todo: for each round the specific apparatus is def. here
    @ManyToMany(cascade=CascadeType.ALL)
    private Set<Order> orders;

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
        //apparatuses.add(Apparatus.BODEN);
        //apparatuses.add(Apparatus.SPRUNG);
        return apparatuses;
    }

    public void setApparatuses(List<Apparatus> apparatuses) {
        this.apparatuses = apparatuses;
    }

    public void addApparatus() {
        apparatuses.add(Apparatus.BODEN);
    }

    public List<Integer> getGymnastIDs() {
        return GymnastIDs;
    }

    public void setGymnastIDs(List<Integer> gymnastIDs) {
        GymnastIDs = gymnastIDs;
    }

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
        List<Apparatus> tempList = new ArrayList<>();
        if (competition.getType() == Type.TURN10) {
            tempList.add(Apparatus.BODEN);
            tempList.add(Apparatus.PFERD);
            tempList.add(Apparatus.RINGE);
            tempList.add(Apparatus.SPRUNG);
            tempList.add(Apparatus.BARREN);
            tempList.add(Apparatus.RECK);
            tempList.add(Apparatus.BALKEN);
            tempList.add(Apparatus.STUFENBARREN);
            tempList.add(Apparatus.MINITRAMPOLIN);
            tempList.add(Apparatus.PAUSE);
        } else if (competition.getType() == Type.STUFENWETTKAMPF) {
            tempList.add(Apparatus.STATION1);
            tempList.add(Apparatus.STATION2);
            tempList.add(Apparatus.STATION3);
            tempList.add(Apparatus.STATION4);
            tempList.add(Apparatus.STATION5);
            tempList.add(Apparatus.STATION6);
            tempList.add(Apparatus.STATION7);
            tempList.add(Apparatus.STATION8);
            tempList.add(Apparatus.STATION9);
            tempList.add(Apparatus.STATION10);
            tempList.add(Apparatus.PAUSE);
        }

        return tempList;
    }
}
