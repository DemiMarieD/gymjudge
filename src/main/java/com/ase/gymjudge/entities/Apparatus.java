package com.ase.gymjudge.entities;

public enum Apparatus {
    //todo
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
