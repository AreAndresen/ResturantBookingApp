package com.skole.s304114mappe2ny.Hovedmenyer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skole.s304114mappe2ny.Fragmenter.NotifikasjonFragment;
import com.skole.s304114mappe2ny.R;


public class MainActivity extends AppCompatActivity {
    //--------KNAPPER-------
    Button resturanterKnapp, vennerKnapp, bestillingKnapp, innstillingerKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //--------KNAPPER--------
        resturanterKnapp = findViewById(R.id.resturanterKnapp);
        vennerKnapp = findViewById(R.id.vennerKnapp);
        bestillingKnapp = findViewById(R.id.bestillingerKnapp);
        innstillingerKnapp = findViewById(R.id.innstillingerKnapp);
        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        resturanterKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (MainActivity.this, Resturanter.class);
                startActivity(intent_startspill);
                //-------VISER DIALOG VED TILBAKEKNAPP---------
            }
        });
        vennerKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_statistikk = new Intent (MainActivity.this, Venner.class);
                startActivity(intent_statistikk);
            }
        });
        bestillingKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_preferanser = new Intent (MainActivity.this, Bestillinger.class);
                startActivity(intent_preferanser);
            }
        });

        innstillingerKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_preferanser = new Intent (MainActivity.this, NotifikasjonFragment.class);
                startActivity(intent_preferanser);
                 //bruker finish her fordi vi kommer tilbake med en ny intent fra preferanser for å oppdatere språk - unngå å legge på stack
            }
        });



        //--------SLUTT LISTENERS--------
    }
    //-------CREATE SLUTTER---------
}


