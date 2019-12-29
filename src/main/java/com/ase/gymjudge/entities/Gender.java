package com.ase.gymjudge.entities;

public enum Gender {
    MALE("male"), FEMALE("female");
    private final String displayValue;
    private Gender(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue() {
        return displayValue;
    }
}
