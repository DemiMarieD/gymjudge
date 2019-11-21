package com.ase.gymjudge.entities;

public enum Type {
    TURN10("Turn10"),
    STUFENWETTKAMPF("Stufenwettkampf");

    private final String displayValue;

    private Type(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
