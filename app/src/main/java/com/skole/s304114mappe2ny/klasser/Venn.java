package com.skole.s304114mappe2ny.klasser;


//------VENN-KLASSE-------
public class Venn {

    //-------ATTRIBUTTER-------
    private long ID;
    private String navn, telefon;


    //-------KONSTRUKTØRER-------
    public Venn() {
        this.ID = ID;
    }

    public Venn(String navn, String telefon) {
        this.navn = navn;
        this.telefon = telefon;
    }

    public Venn(long ID, String navn, String telefon) {
        this.navn = navn;
        this.telefon = telefon;
        this.ID = ID;
    }
    //-------SLUTT KONTRUKSTØRER-------


    //-------GET/SET-------
    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
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
    //-------SLUTT GET/SET-------


    //-------TOSTRING BRUKES I LISTVIEW, DERFOR FÆRRE VERDIER FOR RYDDIGHET-------
    @Override
    public String toString() {
        return getNavn();
    }
}


