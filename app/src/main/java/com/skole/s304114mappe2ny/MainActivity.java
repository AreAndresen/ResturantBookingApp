package com.skole.s304114mappe2ny;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skole.s304114mappe2ny.klasser.Resturant;


public class MainActivity extends AppCompatActivity {


    //--------KNAPPER-------
    Button resturanterKnapp, vennerKnapp, bestillingKnapp, seResturanterKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //--------KNAPPER--------
        resturanterKnapp = findViewById(R.id.resturanterKnapp);
        vennerKnapp = findViewById(R.id.vennerKnapp);
        bestillingKnapp = findViewById(R.id.bestillingKnapp);
        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        resturanterKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (com.skole.s304114mappe2ny.MainActivity.this, Resturanter.class);
                startActivity(intent_startspill);
            }
        });
        vennerKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_statistikk = new Intent (com.skole.s304114mappe2ny.MainActivity.this, Venner.class);
                startActivity(intent_statistikk);
            }
        });
        bestillingKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_preferanser = new Intent (com.skole.s304114mappe2ny.MainActivity.this, LeggTilResturant.class);
                startActivity(intent_preferanser);
                finish(); //bruker finish her fordi vi kommer tilbake med en ny intent fra preferanser for å oppdatere språk - unngå å legge på stack
            }
        });
        //--------SLUTT LISTENERS--------
    }
    //-------CREATE SLUTTER---------
}


