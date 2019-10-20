package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.ListViews.SeVenner;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeResturanter;
import com.skole.s304114mappe2ny.klasser.Resturant;
import com.skole.s304114mappe2ny.klasser.Venn;

public class LeggTilResturant extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DBhandler db;

    private EditText EnavnResturant;
    private EditText EtlfResturant;
    private EditText EtypeResturant;

    private Button btnAdd, btnTilbake;
    //private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legg_til_resturant);

        EnavnResturant = (EditText)findViewById(R.id.navnResturant);
        EtlfResturant = (EditText)findViewById(R.id.tlfResturant);
        EtypeResturant = (EditText)findViewById(R.id.typeResturant);


        btnAdd = (Button) findViewById(R.id.btnLeggTil);
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        db = new DBhandler(this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String newEntry = editText.getText().toString();

                String navn = EnavnResturant.getText().toString();
                String tlf = EtlfResturant.getText().toString();
                String type = EtypeResturant.getText().toString();

                //en liten inputvalidering, ofr kontroll av telefonnummer
                if(!navn.equals("") && !tlf.equals("") && !type.equals("") && tlf.matches(
                        "[0-9\\+\\-\\ ]{2,15}+") && navn.matches("[a-zA-ZæøåÆØÅ\\'\\-\\ \\.]{2,50}+")
                        && type.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{2,50}+")){

                    leggtil(navn, tlf, type);


                } else {
                    toastMessage("Alle felter må fylles ut og navn og telefonnummer må være på gyldig format");
                }

            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_tilbake = new Intent (LeggTilResturant.this, SeResturanter.class);
                startActivity(intent_tilbake);
                finish();
            }
        });

    }


    public void leggtil(String navn, String tlf, String type) {
        //Oppretter nytt objekt
        Resturant nyResturant = new Resturant(navn, tlf, type);
        //legger til i database
        db.leggTilResturant(nyResturant);

        //Resetter inputs
        EnavnResturant.setText("");
        EtlfResturant.setText("");
        EtypeResturant.setText("");

        toastMessage("Resturant lagt til!");
        Log.d("Legg inn: ", "Resturant lagt til");


        Intent intent_tilbake = new Intent (LeggTilResturant.this, SeResturanter.class);
        startActivity(intent_tilbake);
        finish();
    }

    //-------VISER DIALOG VED TILBAKEKNAPP---------
    @Override
    public void onBackPressed() {
        finish();
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
