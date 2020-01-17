package com.ase.gymjudge.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Category {
    //each category has set of apparatus_used and set of participants
    //connected to competition
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "label")
    private String label;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;

    @OneToMany(mappedBy = "category", cascade=CascadeType.ALL)
    private List<Participants> participants;

  //  @NotNull
   /* @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Apparatus> apparatuses;*/

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Competition getCompetition() {
        return competition;
    }

  /*  public Set<Apparatus> getApparatuses() {
        return apparatuses;
    }*/

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

 /*  public void setApparatuses(Set<Apparatus> apparatuses) {
        this.apparatuses = apparatuses;
    }*/

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }
}
