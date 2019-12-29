package com.ase.gymjudge.entities;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public enum Type {
    TURN10("Turn10"),
    STUFENWETTKAMPF("Stufenwettkampf");

    private final String displayValue;
    //

    private Type(String displayValue) {
        this.displayValue = displayValue;
    }
    //private Type(List<Apparatus> apparatuse){
      //  this.apparatuse = apparatuse;
    //}

    public String getDisplayValue() {
        return displayValue;
    }
  //  public List<Apparatus> getApparatuse(){
        //return apparatuse;
    ////}
}
