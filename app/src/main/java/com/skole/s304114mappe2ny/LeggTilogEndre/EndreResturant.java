package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeResturanter;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Deltakelse;
import com.skole.s304114mappe2ny.klasser.Resturant;

import java.util.ArrayList;

public class EndreResturant extends AppCompatActivity {

    private Button btnLagre,btnSlett, btnTilbake;


    private EditText EnavnResturant;
    private EditText EtlfResturant;
    private EditText EtypeResturant;

    DBhandler db;

    private int valgtID;
    private Resturant valgtResturant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endre_resturant);

        btnLagre = (Button) findViewById(R.id.btnLagre);
        btnSlett = (Button) findViewById(R.id.btnSlett);
        btnTilbake = (Button) findViewById(R.id.btnTilbake);


        EnavnResturant = (EditText)findViewById(R.id.navnResturant);
        EtlfResturant = (EditText)findViewById(R.id.tlfResturant);
        EtypeResturant = (EditText)findViewById(R.id.typeResturant);

        db = new DBhandler(this);

        Intent receivedIntent = getIntent();

        valgtID = receivedIntent.getIntExtra("id",0); //NOTE: -1 is just the default value

        valgtResturant = db.finnResturant(valgtID);


        //set the text to show the current selected name
        EnavnResturant.setText(valgtResturant.getNavn());
        EtlfResturant.setText(valgtResturant.getTelefon());
        EtypeResturant.setText(valgtResturant.getType());

        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String navn = EnavnResturant.getText().toString();
                String tlf = EtlfResturant.getText().toString();
                String type = EtypeResturant.getText().toString();

                valgtResturant.setNavn(navn);
                valgtResturant.setTelefon(tlf);
                valgtResturant.setType(type);

                //en liten inputvalidering, ofr kontroll av telefonnummer
                if(!navn.equals("") && !tlf.equals("") && !type.equals("") && tlf.matches(
                        "[0-9\\+\\-\\ ]{2,15}+") && navn.matches("[a-zA-ZæøåÆØÅ\\-\\ \\.]{2,50}+")
                        && type.matches("[a-zA-ZæøåÆØÅ0-9\\-\\ \\.]{2,50}+")){

                    db.oppdaterResturant(valgtResturant);

                    //gjørs så viewet oppdaterer fortløpende
                    Intent intent_tilbake = new Intent (EndreResturant.this, SeResturanter.class);
                    startActivity(intent_tilbake);
                    finish();

                }else{
                    toastMessage("Alle felter må fylles ut og navn og telefonnummer må være på gyldig format");
                }
            }
        });

        btnSlett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.slettResturant(valgtResturant.get_ID());


                //sletter også alle bestillinger til slettet resturant
                ArrayList<Bestilling> bestillinger = db.finnAlleBestillinger();
                for (Bestilling b : bestillinger) {
                    if(b.get_resturantID() == valgtResturant.get_ID()) {

                        //sletter også alle deltakelser til slettet bestilling
                        ArrayList<Deltakelse> deltakelser = db.finnAlleDeltakelser();
                        for (Deltakelse d : deltakelser) {
                            if(d.getBestillingID() == b.get_ID()) {
                                db.slettDeltakelse(d.getID());
                            }
                        }

                        //sletter bestilling
                        db.slettBestilling(b.get_ID());
                    }
                }


                EnavnResturant.setText("");
                EtlfResturant.setText("");
                EtypeResturant.setText("");

                //gjørs så viewet oppdaterer fortløpende
                Intent intent_tilbake = new Intent (EndreResturant.this, SeResturanter.class);
                startActivity(intent_tilbake);
                finish();

                toastMessage("removed from database");
            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gjørs så viewet oppdaterer fortløpende
                Intent intent_tilbake = new Intent (EndreResturant.this, SeResturanter.class);
                startActivity(intent_tilbake);
                finish();
            }
        });

    }

    //-------VISER DIALOG VED TILBAKEKNAPP---------
    @Override
    public void onBackPressed() {
        Intent intent_tilbake = new Intent (EndreResturant.this, SeResturanter.class);
        startActivity(intent_tilbake);
        finish();
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
