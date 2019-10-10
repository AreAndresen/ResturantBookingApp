package com.skole.s304114mappe2ny.Hovedmenyer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skole.s304114mappe2ny.LeggTilogEndre.RegistrerBestilling;
import com.skole.s304114mappe2ny.ListViews.SeBestillinger;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeResturanter;


public class Bestillinger extends AppCompatActivity {


    //--------KNAPPER-------
    Button bestillingKnapp, seBestillingerKnapp, btnTilbake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bestillinger);


        //--------KNAPPER--------
        bestillingKnapp = findViewById(R.id.bestillingKnapp);
        seBestillingerKnapp = findViewById(R.id.seBestillingerKnapp);
        btnTilbake = findViewById(R.id.btnTilbake);
        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        bestillingKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (Bestillinger.this, RegistrerBestilling.class);
                startActivity(intent_startspill);
            }
        });
        //--------LISTENERS--------
        seBestillingerKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (Bestillinger.this, SeBestillinger.class);
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




