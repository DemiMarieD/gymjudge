package com.ase.gymjudge.model;

public class PersonDEMO {
    private String firstName;
    private String lastName;

    public PersonDEMO() {

    }

    public PersonDEMO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
