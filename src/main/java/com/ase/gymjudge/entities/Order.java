package com.ase.gymjudge.entities;

import javax.persistence.*;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //todo each round - apparatus - competition combination should just exist once.
    @ManyToOne(fetch = FetchType.LAZY)
    private Competition competition;
    private int roundNumber;
    private Apparatus apparatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Grouping group;

}
