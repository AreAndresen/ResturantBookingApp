package com.skole.s304114mappe2ny.klasser;


import com.skole.s304114mappe2ny.DBhandler;

import java.util.ArrayList;
import java.util.Arrays;

public class Resturant {

    private long _ID;
    private String navn, telefon, type;


    //konstruktører
    public Resturant() {
        this._ID = _ID;
    }

    public Resturant(String navn, String telefon, String type) {
        this.navn = navn;
        this.telefon = telefon;
        this.type = type;
    }

    public Resturant(long _ID, String navn, String telefon, String type) {
        this.navn = navn;
        this.telefon = telefon;
        this.type = type;
        this._ID = _ID;
    }



    //getter og setter
    public long get_ID() {
        return _ID;
    }
    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getNavn() {
        return navn;
    }
    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return getNavn(); //+ "\nTelefonnummer: " + getTelefon() + "\nType resturant: " + getType()
    }
}

