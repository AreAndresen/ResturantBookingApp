package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeVenner;
import com.skole.s304114mappe2ny.klasser.Venn;


public class LeggTilVenn extends AppCompatActivity {

    //--------KNAPPER--------
    private Button btnLeggTil, btnTilbake;

    //--------TEKST--------
    private EditText EnavnVenn;
    private EditText EtlfVenn;

    //--------DB HANDLER--------
    DBhandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legg_til_venn);

        //--------KNAPPER--------
        btnLeggTil = (Button) findViewById(R.id.btnLeggTil);
        btnTilbake = (Button) findViewById(R.id.btnTilbake);

        //--------INPUTS--------
        EnavnVenn = (EditText)findViewById(R.id.navnVenn);
        EtlfVenn = (EditText)findViewById(R.id.tlfVenn);

        //--------DB HANDLER--------
        db = new DBhandler(this);


        //--------LISTENERS--------
        //KLIKK PÅ LEGG TIL
        btnLeggTil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //FULLFØRER OPPRETTELSE AV NY VENN
                fullforLeggTilVenn();

            }
        });

        //KLIKK PÅ TILBAKE
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
                Intent intent_tilbake = new Intent (LeggTilVenn.this, SeVenner.class);
                startActivity(intent_tilbake);
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------



    //--------METODE FOR Å LEGGE TIL OPPRETTET VENN--------
    private void fullforLeggTilVenn() {
        String navn = EnavnVenn.getText().toString();
        String tlf = EtlfVenn.getText().toString();

        //INPUTVALIDERING
        if (!navn.equals("") && !tlf.equals("") && tlf.matches(
                "[0-9\\+\\-\\ ]{2,15}+") && navn.matches("[a-zA-ZæøåÆØÅ\\'\\-\\ \\.]{2,50}+")) {


            //GENERERER OG LEGGER TIL NY VENN I DB - TAR INN VERDIER TIL NY VENN
            leggtil(navn, tlf);

        } else {
            //INFOMELDING UT - FEIL INPUT
            toastMessage("Alle felter må fylles ut og navn og telefonnummer må være på gyldig format");
        }
    }


    //--------METODE FOR OPPRETTE VENN--------
    public void leggtil(String navn, String tlf) {
        //OPPRETTER NYTT VENN-OBJEKT
        Venn nyVenn = new Venn(navn, tlf);

        //LEGGER TIL NY VENN I DB
        db.leggTilVenn(nyVenn);

        //NULLSTILLER INPUT
        EnavnVenn.setText("");
        EtlfVenn.setText("");

        //INFOMELDING UT
        toastMessage("Venn lagt til!");
        //MELDING TIL LOGG
        Log.d("Legg inn: ", "Venn lagt til");

        //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
        Intent intent_tilbake = new Intent (LeggTilVenn.this, SeVenner.class);
        startActivity(intent_tilbake);
        finish();
    }


    //-------TILBAKE KNAPP - FORHINDRER STACK---------
    @Override
    public void onBackPressed() {
        finish();
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}