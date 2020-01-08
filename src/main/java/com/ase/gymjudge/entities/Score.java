package com.ase.gymjudge.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Score implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Participants participants;

    @Enumerated(EnumType.STRING)
    private Apparatus apparatus;

    @NotNull
    private int status;

    // point system
    private float d;
    private float e1;
    private float e2;
    private float e3;
    private float e4;
    private float n;

    // for ordering
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    @NotNull
    private Date date;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        date = new Date();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Participants getParticipants() {
        return participants;
    }

    public void setParticipants(Participants participants) {
        this.participants = participants;
    }

    public Apparatus getApparatus() {
        return apparatus;
    }

    public void setApparatus(Apparatus apparatus) {
        this.apparatus = apparatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public float getE1() {
        return e1;
    }

    public void setE1(float e1) {
        this.e1 = e1;
    }

    public float getE2() {
        return e2;
    }

    public void setE2(float e2) {
        this.e2 = e2;
    }

    public float getE3() {
        return e3;
    }

    public void setE3(float e3) {
        this.e3 = e3;
    }

    public float getE4() {
        return e4;
    }

    public void setE4(float e4) {
        this.e4 = e4;
    }

    public float getN() {
        return n;
    }

    public void setN(float n) {
        this.n = n;
    }

    public Date getDate() {
        return date;
    }
}
