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

    private Button btnSave,btnDelete;


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

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);


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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String navn = EnavnResturant.getText().toString();
                String tlf = EtlfResturant.getText().toString();
                String type = EtypeResturant.getText().toString();

                valgtResturant.setNavn(navn);
                valgtResturant.setTelefon(tlf);
                valgtResturant.setType(type);

                if(!navn.equals("") && !tlf.equals("") && !type.equals("")){

                    db.oppdaterResturant(valgtResturant);

                    //gjørs så viewet oppdaterer fortløpende
                    Intent intent_tilbake = new Intent (EndreResturant.this, SeResturanter.class);
                    startActivity(intent_tilbake);
                    finish();

                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
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
