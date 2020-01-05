package com.ase.gymjudge.entities;

public enum Apparatus {
    // TODO: List possible Apparatuses for other Types (Stufe) as well
    BODEN("Boden"),
    PFERD("Pauschenpferd"),
    RINGE("Ringe"),
    SPRUNG("Sprung"),
    BARREN("Barren"),
    RECK("Reck"),
    BALKEN("Balken"),
    STUFENBARREN("Stufenbarren"),
    MINITRAMPOLIN("Minitrampolin"),
    PAUSE("Pause"),

    STATION1("Station1"),
    STATION2("Station2"),
    STATION3("Station3"),
    STATION4("Station4"),
    STATION5("Station5"),
    STATION6("Station6"),
    STATION7("Station7"),
    STATION8("Station8"),
    STATION9("Station9"),
    STATION10("Station10");


    private final String displayValue;

    private Apparatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
