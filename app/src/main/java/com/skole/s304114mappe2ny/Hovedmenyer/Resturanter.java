package com.skole.s304114mappe2ny.Hovedmenyer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skole.s304114mappe2ny.LeggTilogEndre.LeggTilResturant;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeResturanter;


public class Resturanter extends AppCompatActivity {


    //--------KNAPPER-------
    Button resturanterKnapp, seResturanterKnapp, btnTilbake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturanter);


        //--------KNAPPER--------
        resturanterKnapp = findViewById(R.id.resturanterKnapp);
        seResturanterKnapp = findViewById(R.id.seResturanterKnapp);
        btnTilbake = findViewById(R.id.btnTilbake);
        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        resturanterKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (Resturanter.this, LeggTilResturant.class);
                startActivity(intent_startspill);
            }
        });

        //--------LISTENERS--------
        seResturanterKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (Resturanter.this, SeResturanter.class);
                startActivity(intent_startspill);
            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //--------SLUTT LISTENERS--------
    }
    //-------CREATE SLUTTER---------

    //-------VISER DIALOG VED TILBAKEKNAPP---------
    @Override
    public void onBackPressed() {
        finish();
    }
}



