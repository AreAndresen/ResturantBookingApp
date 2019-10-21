package com.skole.s304114mappe2ny.Hovedmenyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.skole.s304114mappe2ny.LeggTilogEndre.LeggTilVenn;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeVenner;

public class Venner extends AppCompatActivity {


    //--------KNAPPER-------
    Button vennerKnapp, seVennerKnapp, btnTilbake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venner);


        //--------KNAPPER--------
        vennerKnapp = findViewById(R.id.vennerKnapp);
        seVennerKnapp = findViewById(R.id.seVennerKnapp);
        btnTilbake = findViewById(R.id.btnTilbake);

        //--------SLUTT KNAPPER--------


        //--------LISTENERS--------
        vennerKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (Venner.this, LeggTilVenn.class);
                startActivity(intent_startspill);

            }
        });
        //--------LISTENERS--------
        seVennerKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (Venner.this, SeVenner.class);
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
