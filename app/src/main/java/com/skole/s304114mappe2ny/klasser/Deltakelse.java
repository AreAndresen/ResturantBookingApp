package com.skole.s304114mappe2ny.klasser;



//-------DELTAKELSE-KLASSE-------
public class Deltakelse {

    //-------ATTRIBUTTER-------
    private long ID, bestillingID, vennID;
    private String vennNavn;


    //-------KONSTRUKTØRER-------
    public Deltakelse() {
        this.ID = ID;
    }

    public Deltakelse(long bestillingID, long vennID, String vennNavn) {
        this.bestillingID = bestillingID;
        this.vennID = vennID;
        this.vennNavn = vennNavn;
    }

    //FJERN
    public Deltakelse(long ID, long bestillingID, long vennID, String vennNavn) {
        this.ID = ID;
        this.bestillingID = bestillingID;
        this.vennID = vennID;
        this.vennNavn = vennNavn;
    }
    //-------SLUTT KONTRUKSTØRER-------


    //-------GET/SET-------
    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }

    public long getBestillingID() {
        return bestillingID;
    }
    public void setBestillingID(long bestillingID) {
        this.bestillingID = bestillingID;
    }

    public long getVennID() {
        return vennID;
    }
    public void setVennID(long vennID) {
        this.vennID = vennID;
    }

    public String getVennNavn() {
        return vennNavn;
    }
    public void setVennNavn(String VennNavn) {
        this.vennNavn = VennNavn;
    }
    //-------SLUTT GET/SET-------


    //-------TOSTRING BRUKES I LISTVIEW, DERFOR FÆRRE VERDIER FOR RYDDIGHET-------
    @Override
    public String toString() {
        return "Deltakelseid: " +getID()+" Navn: "+getVennNavn();
    }
}

