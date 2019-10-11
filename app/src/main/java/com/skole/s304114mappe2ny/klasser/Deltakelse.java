package com.skole.s304114mappe2ny.klasser;

public class Deltakelse {

    private long ID;
    private long bestillingID;
    private long vennID;



    //konstruktører
    public Deltakelse() {
        this.ID = ID;
    }

    public Deltakelse(long bestillingID, long vennID) {
        this.bestillingID = bestillingID;
        this.vennID = vennID;
    }

    public Deltakelse(long ID, long bestillingID, long vennID) {
        this.ID = ID;
        this.bestillingID = bestillingID;
        this.vennID = vennID;
    }


    //getter og setter
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



    @Override
    public String toString() {
        return "Deltakelse: " +getID()+ ". Bestilling: "+getBestillingID()+". Personid: "+getVennID();
    }
}

