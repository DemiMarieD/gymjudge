package com.ase.gymjudge.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    private Integer adminID;
    private String description;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull
    private Date endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany (mappedBy = "competition", cascade= CascadeType.ALL)
    private List<Participants> participants;

    @OneToMany (mappedBy = "competition", cascade= CascadeType.ALL)
    private List<Category> categories;

    @OneToMany (mappedBy = "competition", cascade= CascadeType.ALL)
    private List<Grouping> groups;

    @OneToMany(mappedBy = "competition", cascade= CascadeType.ALL)
    private List<User> judges;
    public void setJudges(List<User> judges) {
        this.judges = judges;
    }
    public List<User> getJudges() {
        return judges;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String startDate) throws ParseException {
        this.startDate = new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
    }

    public void setEndDate(String endDate) throws ParseException {
        this.endDate = new SimpleDateFormat("yyyy-mm-dd").parse(endDate);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setParticipants(List<Participants> participants) {
        this.participants = participants;
    }

    public List<Participants> getParticipants() {
        return participants;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Grouping> getGroups() {
        return groups;
    }

    public void setGroups(List<Grouping> groups) {
        this.groups = groups;
    }

    public List<Integer> getGroupingOrderFor(Apparatus apparatus) {
        List<Integer> order = new ArrayList<>();
        int maxApparatuses = 0;
        boolean apparatusFound = false;

        for (Grouping group: groups) {
            if (group.getApparatuses().size() > maxApparatuses) {
                maxApparatuses = group.getApparatuses().size();
            }
        }

        for (int i = 0; i < maxApparatuses; i++) {
            for (Grouping group: groups) {
                if (group.getApparatuses().indexOf(apparatus) == i) {
                    order.add(group.getId());
                    apparatusFound = true;
                    break;
                }
            }
            if (!apparatusFound) {
                order.add(-1);
            }
            apparatusFound = false;
        }

        return order;
    }

    public List<Apparatus> getAvailableApparatuses() {
        List<Apparatus> tempList = new ArrayList<>();
        if (type == Type.TURN10) {
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
        } else if (type == Type.STUFENWETTKAMPF) {
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
