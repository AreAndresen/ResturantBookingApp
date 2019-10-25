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
    Button leggTilResturanterKnapp, seResturanterKnapp, btnTilbake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturanter);


        //--------KNAPPER--------
        leggTilResturanterKnapp = findViewById(R.id.leggTilResturanterKnapp);
        seResturanterKnapp = findViewById(R.id.seResturanterKnapp);
        btnTilbake = findViewById(R.id.btnTilbake);


        //--------LISTENERS--------
        //KLIKK LEGG TIL RESTURANT
        leggTilResturanterKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (Resturanter.this, LeggTilResturant.class);
                startActivity(intent_startspill);
                finish();
            }
        });

        //KLIKK SE RESTURANTER
        seResturanterKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_startspill = new Intent (Resturanter.this, SeResturanter.class);
                startActivity(intent_startspill);
                finish();
            }
        });

        //KLIKK TILBAKE TIL START
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Resturanter.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------


    //-------TILBAKEKNAPP - FORHINDRER STACK (FINISH)---------
    @Override
    public void onBackPressed() {
        Intent intent = new Intent (Resturanter.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}