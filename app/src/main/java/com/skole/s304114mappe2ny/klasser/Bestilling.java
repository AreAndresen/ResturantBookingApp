package com.skole.s304114mappe2ny.klasser;



import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Bestilling {

    private long _ID;
    private Date dato;
    private Time tid;
    private Resturant resturant;
    private ArrayList<Venn> venner = new ArrayList<>();

    //konstruktører
    public Bestilling() {
        this._ID = _ID;
    }

    public Bestilling(Date dato, Time tid, Resturant resturanten, ArrayList<Venn> venner) {
        this.dato = dato;
        this.tid = tid;
        this.resturant = resturanten;
        this.venner = venner;
    }

    public Bestilling(long _ID, Date dato, Time tid, Resturant resturanten, ArrayList<Venn> venner) {
        this.dato = dato;
        this.tid = tid;
        this.resturant = resturanten;
        this.venner = venner;
    }


    //getter og setter
    public long get_ID() {
        return _ID;
    }
    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public Date getDato() { return dato; }
    public void setDato(Date dato) { this.dato = dato; }

    public Time getTid() { return tid; }
    public void setTid(Date dato) { this.tid = tid; }

    public Resturant getResturant(){ return resturant; }
    public void setResturant(Resturant resturant) { this.resturant = resturant; }

    public ArrayList<Venn> getVenner(){
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
    }

    @Override
    public String toString() {
        return "Dato: "+getDato()+ "Tidspunkt: "+getTid()+ "Resturant: "+"Venner: "+alleVenner();
    }

}


