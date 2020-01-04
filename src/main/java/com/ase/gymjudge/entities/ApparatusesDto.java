package com.ase.gymjudge.entities;

import java.util.ArrayList;
import java.util.List;

public class ApparatusesDto {
    private List<Apparatus> apparatuses;

    public ApparatusesDto() {
        this.apparatuses = new ArrayList<>();
    }

    public ApparatusesDto(List<Apparatus> apparatuses) {
        this.apparatuses = apparatuses;
    }

    public void addApparatus(Apparatus apparatus) {
        this.apparatuses.add(apparatus);
    }

    public List<Apparatus> getApparatuses() {
        return apparatuses;
    }

    public void setApparatuses(List<Apparatus> apparatuses) {
        this.apparatuses = apparatuses;
    }
}
