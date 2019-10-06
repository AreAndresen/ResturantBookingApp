package com.skole.s304114mappe2ny.Hovedmenyer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skole.s304114mappe2ny.LeggTilogEndre.LeggTilResturant;
import com.skole.s304114mappe2ny.R;


public class MainActivity extends AppCompatActivity {


    //--------KNAPPER-------
    Button resturanterKnapp, vennerKnapp, bestillingKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //--------KNAPPER--------
        resturanterKnapp = findViewById(R.id.resturanterKnapp);
        vennerKnapp = findViewById(R.id.vennerKnapp);
        bestillingKnapp = findViewById(R.id.bestillingerKnapp);
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
        //--------SLUTT LISTENERS--------
    }
    //-------CREATE SLUTTER---------
}

