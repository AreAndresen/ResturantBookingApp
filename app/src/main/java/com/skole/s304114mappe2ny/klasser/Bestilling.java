package com.skole.s304114mappe2ny.klasser;



import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Bestilling {

    private long _ID;
    private String dato;
    private String tid;
    private String resturantNavn;
    private long resturantID;

    //konstruktører
    public Bestilling() {
        this._ID = _ID;
    }

    public Bestilling(String dato, String tid, String resturantNavn, long resturantID) {
        this.dato = dato;
        this.tid = tid;
        this.resturantNavn = resturantNavn;
        this.resturantID = resturantID;
    }

    public Bestilling(long _ID, String dato, String tid, String resturantNavn, long resturantID) {
        this.dato = dato;
        this.tid = tid;
        this.resturantNavn = resturantNavn;
        this.resturantID = resturantID;
        this._ID = _ID;
    }


    //getter og setter
    public long get_ID() {
        return _ID;
    }
    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String getDato() { return dato; }
    public void setDato(String dato) { this.dato = dato; }

    public String getTid() { return tid; }
    public void setTid(String tid) { this.tid = tid; }

    public String getResturantNavn() { return resturantNavn; }
    public void setResturantNavn(String resturantNavn) { this.resturantNavn = resturantNavn; }

    public long get_resturantID() {
        return resturantID;
    }
    public void set_resturantID(long resturantID) {
        this.resturantID = resturantID;
    }


    @Override
    public String toString() {
        return "Nr: "+get_ID()+". Dato: "+getDato()+ ". Tidspunkt: "+getTid()+"."; // "Resturant: "+get_ID()
    }

}


