package com.skole.s304114mappe2ny;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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


    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Resturanter";


    String LAG_TABELL = "CREATE TABLE " + TABLE_RESTURANTER + "(" + KEY_ID +
            " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT," + KEY_TYPE + " TEXT" + ")";

    String LAG_VENNER = "CREATE TABLE " + TABLE_VENNER + "(" + VENN_ID +
            " INTEGER PRIMARY KEY," + VENN_NAME + " TEXT," + VENN_TLF + " TEXT" + ")";


    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //sql setning:

        Log.d("SQL", LAG_TABELL);
        Log.d("SQL", LAG_VENNER);

        db.execSQL(LAG_VENNER);
        db.execSQL(LAG_TABELL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTURANTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENNER);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENNER);

        onCreate(db);
    }

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

    //går inn i db og henter alle resturanter
    public ArrayList<Resturant> finnAlleResturanter() {
        ArrayList<Resturant> resturantListe = new ArrayList<Resturant>();
        String selectQuery= "SELECT * FROM " + TABLE_RESTURANTER;
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

    //går inn i db og henter alle resturanter
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



    public void slettResturant(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTURANTER, KEY_ID + " =? ", new String[]{Long.toString(inn_id)}); //KAN OGSÅ ENDRE TIL NAVN HR ISTDEN FOR ID
        db.close();
    }

    public void slettVenn(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VENNER, VENN_ID + " =? ", new String[]{Long.toString(inn_id)}); //KAN OGSÅ ENDRE TIL NAVN HR ISTDEN FOR ID
        db.close();
    }


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



    /*public Resturant finnResturant2(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RESTURANTER +
                " WHERE " + KEY_ID + " = '" + id + "'";
        Resturant Resturant = db.rawQuery(query, null);
        return Resturant;
    }*/
    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RESTURANTER;
        Cursor data = db.rawQuery(query, null);
        return data;
    }



    //Denne brukes
    public void slettResturant2(Resturant resturant) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTURANTER, KEY_NAME + " =? ", new String[]{String.valueOf(resturant.get_ID())
        }); //KAN OGSÅ ENDRE TIL NAVN HR ISTDEN FOR ID
        db.close();
    }




    public int finnAntallResturanter() {
        String sql= "SELECT * FROM " + TABLE_RESTURANTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int antall = cursor.getCount();
        cursor.close();
        db.close();
        return antall;
    }









} // Slutt på DBHandler
