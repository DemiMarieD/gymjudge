package com.ase.gymjudge.entities;

public enum Apparatus {
    // TODO: List possible Apparatuses for other Types (Stufe) as well
    BODEN("Boden"),
    PFERD("Pauschenpferd"),
    RINGE("Ringe"),
    SPRUNG("Sprung"),
    BARREN("Barren"),
    RECK("Reck"),
    SCHWEBEBALKEN("Schwebebalken"),
    STUFENBARREN("Stufenbarren"),
    MINITRAMPOLIN("Minitrampolin"),
    PAUSE("Pause");

    private final String displayValue;

    private Apparatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}