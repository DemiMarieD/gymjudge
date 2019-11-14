package com.ase.gymjudge.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class CompetitionDEMO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer competitionId;

    private Integer competitionDetailsId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Date start;

    private Date end;

    private int type;

    private CompetitionStates status;

    public CompetitionDEMO() {
        this.status = CompetitionStates.CREATED;
    }

    public CompetitionDEMO(@NotBlank Integer competitionDetailsId, @NotBlank String name, @NotBlank String description, @NotBlank Date start, @NotBlank Date end, @NotBlank int type, @NotBlank CompetitionStates status) {
        this.competitionDetailsId = competitionDetailsId;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.type = type;
        this.status = status;
    }

    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    public Integer getCompetitionDetailsId() {
        return competitionDetailsId;
    }

    public void setCompetitionDetailsId(Integer competitionDetailsId) {
        this.competitionDetailsId = competitionDetailsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(String start) throws ParseException {
        this.start = new SimpleDateFormat("yyyy-mm-dd").parse(start);
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(String end) throws ParseException {
        this.end = new SimpleDateFormat("yyyy-mm-dd").parse(end);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CompetitionStates getStatus() {
        return status;
    }

    public void setStatus(CompetitionStates status) {
        this.status = status;
    }
}
