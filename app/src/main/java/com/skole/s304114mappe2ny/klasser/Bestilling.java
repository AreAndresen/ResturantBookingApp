package com.skole.s304114mappe2ny.klasser;



//-------BESTILLING-KLASSE-------
public class Bestilling {

    //-------ATTRIBUTTER-------
    private long _ID;
    private String dato, tid, resturantNavn;
    private long resturantID;


    //-------KONSTRUKTØRER-------
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
    //-------SLUTT KONTRUKSTØRER-------


    //-------GET/SET-------
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
    //-------SLUTT GET/SET-------


    //-------TOSTRING BRUKES I LISTVIEW, DERFOR FÆRRE VERDIER FOR RYDDIGHET-------
    @Override
    public String toString() {
        return "Nr: "+get_ID()+". Dato: "+getDato()+ ". Tidspunkt: "+getTid()+".";
    }
}


