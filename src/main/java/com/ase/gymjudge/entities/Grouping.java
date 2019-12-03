package com.ase.gymjudge.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Grouping {
    //each group has set of participants
    //order of apperatus
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade= CascadeType.ALL)
    private List<Participants> participants;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }
}
