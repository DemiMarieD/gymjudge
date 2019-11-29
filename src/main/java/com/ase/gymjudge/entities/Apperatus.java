package com.ase.gymjudge.entities;

public enum Apperatus {
    //todo
    BARREN(""),
    SCHWEBEBALCKEN("");

    private final String displayValue;

    private Apperatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
