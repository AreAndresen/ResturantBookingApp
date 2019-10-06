package com.skole.s304114mappe2ny;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Resturanter extends AppCompatActivity {


    //--------KNAPPER-------
    Button resturanterKnapp, seResturanterKnapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturanter);


        //--------KNAPPER--------
        resturanterKnapp = findViewById(R.id.resturanterKnapp);
        seResturanterKnapp = findViewById(R.id.seResturanterKnapp);
        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        resturanterKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (com.skole.s304114mappe2ny.Resturanter.this, LeggTilResturant.class);
                startActivity(intent_startspill);
            }
        });
        //--------LISTENERS--------
        seResturanterKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (com.skole.s304114mappe2ny.Resturanter.this, SeResturanter.class);
                startActivity(intent_startspill);
            }
        });
        //--------SLUTT LISTENERS--------
    }
    //-------CREATE SLUTTER---------
}



