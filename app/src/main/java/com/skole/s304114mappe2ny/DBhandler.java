package com.skole.s304114mappe2ny;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Deltakelse;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;
import java.util.ArrayList;

public class DBhandler extends SQLiteOpenHelper{

    static String TABLE_RESTURANTER = "Resturant";
    static String KEY_ID = "_ID";
    static String KEY_NAME = "Navn";
    static String KEY_PH_NO = "Telefon";
    static String KEY_TYPE = "Type";

    static String TABLE_VENNER = "Venner";
    static String VENN_ID = "ID";
    static String VENN_NAME = "Navnet";
    static String VENN_TLF = "Tlf";

    static String TABLE_BESTILLINGER = "Bestillinger";
    static String BESTILLING_ID = "IDen";
    static String BESTILLING_DATO = "Dato";
    static String BESTILLING_TID = "Tidspunkt";
    static String BESTILLING_RESTURANTNAVN = "Resturantnavn";
    static String BESTILLING_RESTURANT = "Resturanten_ID";

    static String TABLE_DELTAKELSE = "Deltakelse";
    static String DELTAKELSE_ID = "Deltakelse_id";
    static String DTK_BESTILLING_ID = "dtk_bestilling_ID";
    static String DTK_PERSON_ID = "dtk_person_id";
    static String DTK_PERSON_NAVN = "dtk_person_navn";



    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Resturanter";


    String LAG_RESTURANTER = "CREATE TABLE " + TABLE_RESTURANTER + "(" + KEY_ID +
            " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT," + KEY_TYPE + " TEXT" + ")";

    String LAG_VENNER = "CREATE TABLE " + TABLE_VENNER + "(" + VENN_ID +
            " INTEGER PRIMARY KEY," + VENN_NAME + " TEXT," + VENN_TLF + " TEXT" + ")";

    String LAG_BESTILLINGER = "CREATE TABLE " + TABLE_BESTILLINGER + "(" + BESTILLING_ID +
            " INTEGER PRIMARY KEY," + BESTILLING_DATO + " TEXT," + BESTILLING_TID + " TEXT," + BESTILLING_RESTURANTNAVN + " TEXT," + BESTILLING_RESTURANT + " " +
            " INTEGER, FOREIGN KEY(Resturanten_ID) REFERENCES TABLE_RESTURANTER (KEY_ID)" + ")";

    String LAG_DELTAKELSE = "CREATE TABLE " + TABLE_DELTAKELSE + "(" + DELTAKELSE_ID +
            " INTEGER PRIMARY KEY," + DTK_BESTILLING_ID + " INTEGER, " + DTK_PERSON_ID + " INTEGER, " + DTK_PERSON_NAVN + " "+
            " TEXT, FOREIGN KEY(dtk_bestilling_ID) REFERENCES TABLE_BESTILLINGER (BESTILLING_ID)," +
            " FOREIGN KEY(dtk_person_id) REFERENCES TABLE_VENNER (VENN_ID)" + ")";


    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //sql setning:

        Log.d("SQL", LAG_RESTURANTER);
        Log.d("SQL", LAG_VENNER);
        Log.d("SQL", LAG_BESTILLINGER);

        db.execSQL(LAG_VENNER);
        db.execSQL(LAG_RESTURANTER);
        db.execSQL(LAG_BESTILLINGER);
        db.execSQL(LAG_DELTAKELSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTURANTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENNER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BESTILLINGER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELTAKELSE);

        onCreate(db);
    }


    //-----------LEGG TIL METODER------------
    public void leggTilResturant(Resturant resturant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, resturant.getNavn());
        values.put(KEY_PH_NO, resturant.getTelefon());
        values.put(KEY_TYPE, resturant.getType());
        db.insert(TABLE_RESTURANTER, null, values);
        db.close();
    }

    public void leggTilVenn(Venn venn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VENN_NAME, venn.getNavn());
        values.put(VENN_TLF, venn.getTelefon());
        db.insert(TABLE_VENNER, null, values);
        db.close();
    }

    public void leggTilBestilling(Bestilling bestilling, long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BESTILLING_ID, id); //autogenererer IKKE Denne
        values.put(BESTILLING_DATO, bestilling.getDato());
        values.put(BESTILLING_TID, bestilling.getTid());
        values.put(BESTILLING_RESTURANTNAVN, bestilling.getResturantNavn());
        values.put(BESTILLING_RESTURANT, bestilling.get_resturantID());
        db.insert(TABLE_BESTILLINGER, null, values);
        db.close();
    }

    public void leggTilDeltakelse(Deltakelse deltakelse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DTK_BESTILLING_ID, deltakelse.getBestillingID());
        values.put(DTK_PERSON_ID , deltakelse.getVennID());
        values.put(DTK_PERSON_NAVN , deltakelse.getVennNavn());
        db.insert(TABLE_DELTAKELSE, null, values);
        db.close();
    }


    //-----------FINN ALLE METODER------------
    public ArrayList<Resturant> finnAlleResturanter() {
        ArrayList<Resturant> resturantListe = new ArrayList<Resturant>();
        String selectQuery = "SELECT * FROM " + TABLE_RESTURANTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {Resturant resturant = new Resturant();
                resturant.set_ID(cursor.getLong(0));
                resturant.setNavn(cursor.getString(1));
                resturant.setTelefon(cursor.getString(2));
                resturant.setType(cursor.getString(3));

                resturantListe.add(resturant);
            }
            while(cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return resturantListe;
    }

    public ArrayList<Venn> finnAlleVenner() {
        ArrayList<Venn> vennerListe = new ArrayList<Venn>();
        String selectQuery= "SELECT * FROM " + TABLE_VENNER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {Venn venn = new Venn();
                venn.setID(cursor.getLong(0));
                venn.setNavn(cursor.getString(1));
                venn.setTelefon(cursor.getString(2));

                vennerListe.add(venn);
            }
            while(cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return vennerListe;
    }



    public ArrayList<Bestilling> finnAlleBestillinger() {
        ArrayList<Bestilling> bestillingerListe = new ArrayList<Bestilling>();
        String selectQuery= "SELECT * FROM " + TABLE_BESTILLINGER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {Bestilling bestilling = new Bestilling();
                bestilling.set_ID(cursor.getLong(0));
                bestilling.setDato((cursor.getString(1)));
                bestilling.setTid((cursor.getString(2)));
                bestilling.setResturantNavn(cursor.getString(3));
                bestilling.set_resturantID((cursor.getLong(4)));
                bestillingerListe.add(bestilling);
            }
            while(cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return bestillingerListe;
    }

    public ArrayList<Deltakelse> finnAlleDeltakelser() {
        ArrayList<Deltakelse> deltakelseListe = new ArrayList<Deltakelse>();
        String selectQuery= "SELECT * FROM " + TABLE_DELTAKELSE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {Deltakelse deltakelse = new Deltakelse();
                deltakelse.setID(cursor.getLong(0));
                deltakelse.setBestillingID(cursor.getLong(1));
                deltakelse.setVennID(cursor.getLong(2));
                deltakelse.setVennNavn((cursor.getString(3)));
                deltakelseListe.add(deltakelse);
            }
            while(cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return deltakelseListe;
    }


    //-----------FINN EN METODER------------
    public Resturant finnResturant(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_RESTURANTER, new String[]{
                KEY_ID, KEY_NAME, KEY_PH_NO, KEY_TYPE}, KEY_ID + "=?", new String[]{String.valueOf(id)
        }, null, null, null, null);
        if(cursor!= null)
            cursor.moveToFirst();
        Resturant resturant = new Resturant(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return resturant;
    }

    public Venn finnVenn(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_VENNER, new String[]{
                VENN_ID, VENN_NAME, VENN_TLF}, VENN_ID + "=?", new String[]{String.valueOf(id)
        }, null, null, null, null);
        if(cursor!= null)
            cursor.moveToFirst();
        Venn venn = new Venn(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return venn;
    }

    public Bestilling finnBestilling(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_BESTILLINGER, new String[]{
                BESTILLING_ID, BESTILLING_DATO, BESTILLING_TID, BESTILLING_RESTURANTNAVN, BESTILLING_RESTURANT}, BESTILLING_ID + "=?", new String[]{String.valueOf(id)
        }, null, null, null, null);
        if(cursor!= null)
            cursor.moveToFirst();
        Bestilling bestilling = new Bestilling(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                Long.parseLong(cursor.getString(4)));
        cursor.close();
        db.close();
        return bestilling;
    }


    //-----------SLETT METODER------------
    public void slettResturant(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTURANTER, KEY_ID + " =? ", new String[]{Long.toString(inn_id)});
        db.close();
    }

    public void slettVenn(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VENNER, VENN_ID + " =? ", new String[]{Long.toString(inn_id)});
        db.close();
    }

    public void slettBestilling(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BESTILLINGER, BESTILLING_ID + " =? ", new String[]{Long.toString(inn_id)});
        db.close();
    }

    public void slettDeltakelse(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DELTAKELSE, DELTAKELSE_ID + " =? ", new String[]{Long.toString(inn_id)});
        db.close();
    }


    //-----------OPPDATER METODER------------
    public int oppdaterResturant(Resturant resturant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, resturant.getNavn());
        values.put(KEY_PH_NO, resturant.getTelefon());
        values.put(KEY_TYPE, resturant.getType());
        int endret = db.update(TABLE_RESTURANTER, values, KEY_ID + "= ?", new String[]{String.valueOf(resturant.get_ID())
        });
        db.close();
        return endret;
    }

    public int oppdaterVenn(Venn venn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VENN_NAME, venn.getNavn());
        values.put(VENN_TLF, venn.getTelefon());
        int endret = db.update(TABLE_VENNER, values, VENN_ID + "= ?", new String[]{String.valueOf(venn.getID())
        });
        db.close();
        return endret;
    }
} // Slutt på DBHandler
