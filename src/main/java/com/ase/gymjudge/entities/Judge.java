package com.ase.gymjudge.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Judge {
    //#depending on type of competitions
    //is connected to competition
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


}
