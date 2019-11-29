package com.ase.gymjudge.entities;

public enum Apparatus {
    //todo fill in more later
    BARREN("Barren"),
    SCHWEBEBALKEN("Schwebebalken");

    private final String displayValue;

    private Apparatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
