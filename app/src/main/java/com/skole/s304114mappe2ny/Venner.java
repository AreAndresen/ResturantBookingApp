package com.skole.s304114mappe2ny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Venner extends AppCompatActivity {


    //--------KNAPPER-------
    Button vennerKnapp, seVennerKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venner);


        //--------KNAPPER--------
        vennerKnapp = findViewById(R.id.vennerKnapp);
        seVennerKnapp = findViewById(R.id.seVennerKnapp);
        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        vennerKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (com.skole.s304114mappe2ny.Venner.this, LeggTilVenn.class);
                startActivity(intent_startspill);
            }
        });
        //--------LISTENERS--------
        seVennerKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (com.skole.s304114mappe2ny.Venner.this, SeVenner.class);
                startActivity(intent_startspill);
            }
        });
        //--------SLUTT LISTENERS--------
    }
    //-------CREATE SLUTTER---------
}
