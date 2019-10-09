package com.skole.s304114mappe2ny.klasser;



import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Bestilling {

    private long _ID;
    //private Date dato; //
    private String dato;
    //private Time tid;
    private String tid;
    private long resturantID;
    //private Resturant resturant;// long resturantID
    private String venner;
    //private ArrayList<Venn> venner = new ArrayList<>();

    //konstruktører
    public Bestilling() {
        this._ID = _ID;
    }

    public Bestilling(String dato, String tid, long resturantID, String venner) { //Date dato, Time tid, Resturant resturanten, ArrayList<Venn> venner
        this.dato = dato;
        this.tid = tid;
        //this.resturant = resturanten;
        this.resturantID = resturantID;
        this.venner = venner;
    }

    public Bestilling(long _ID, String dato, String tid, long resturantID , String venner) { //
        this.dato = dato;
        this.tid = tid;
        //this.resturant = resturanten;
        this.resturantID = resturantID;
        this.venner = venner;
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

    public long get_resturantID() {
        return resturantID;
    }
    public void set_resturantID(long resturantID) {
        this.resturantID = resturantID;
    }

    public String getVenner() { return venner; }
    public void setVenner(String venner) { this.venner = venner; }


    /*public Resturant getResturant(){ return resturant; }
    public void setResturant(Resturant resturant) { this.resturant = resturant; }*/

    /*public ArrayList<Venn> getVenner(){
        return venner;
    }

    public void leggTilVenn(Venn enVenn){
        venner.add(enVenn);
    }

    public String alleVenner() {
        String ut = "";
        for(Venn enVenn : venner){
            ut += enVenn.getNavn()+", ";
        }
        return ut;
    }

    public void slettVenn(long id){
        Venn vennen = null;

        for(Venn enVenn : venner){
            if(enVenn.getID()== id){
                vennen = enVenn;
                break;
            }
        }
        if(vennen != null){
            venner.remove(vennen);
        }
    }*/

    @Override
    public String toString() {
        return "Dato: "+getDato()+ "Tidspunkt: "+getTid()+ "Resturant: "+"Venner: "+getVenner(); //alleVenner()
    }

}


