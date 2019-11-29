package com.ase.gymjudge.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @ManyToOne(cascade= CascadeType.ALL)
    private Competition competition;

    @OneToMany(cascade= CascadeType.ALL)
    private Set<Participants> participants;

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Apparatus> apparatuses;

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Competition getCompetition() {
        return competition;
    }

    public Set<Apparatus> getApparatuses() {
        return apparatuses;
    }

    public Set<Participants> getParticipants() {
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

    public void setApparatuses(Set<Apparatus> apparatuses) {
        this.apparatuses = apparatuses;
    }

    public void setParticipants(Set<Participants> participants) {
        this.participants = participants;
    }
}
