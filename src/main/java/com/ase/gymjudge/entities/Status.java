package com.ase.gymjudge.entities;

public enum Status {
    CREATED("Created"),
    OPEN("Open"),
    CLOSED("Closed"),
    ACTIVE("Active"),
    FINISHED("Finished");

    private final String displayValue;

    private Status(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
