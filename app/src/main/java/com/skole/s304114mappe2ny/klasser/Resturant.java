package com.skole.s304114mappe2ny.klasser;


//-------RESTURANT-KLASSE-------
public class Resturant {

    //-------ATTRIBUTTER-------
    private long _ID;
    private String navn, telefon, type;


    //-------KONSTRUKTØRER-------
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
    //-------SLUTT KONTRUKSTØRER-------


    //-------GET/SET-------
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
    //-------SLUTT GET/SET-------


    //-------TOSTRING BRUKES I LISTVIEW, DERFOR FÆRRE VERDIER FOR RYDDIGHET-------
    @Override
    public String toString() {
        return getNavn();
    }
}

