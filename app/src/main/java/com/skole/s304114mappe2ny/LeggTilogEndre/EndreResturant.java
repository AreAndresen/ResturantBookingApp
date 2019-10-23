package com.skole.s304114mappe2ny.LeggTilogEndre;


import androidx.appcompat.app.AppCompatActivity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeResturanter;
import com.skole.s304114mappe2ny.SlettDialoger.SlettResturantDialog;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Deltakelse;
import com.skole.s304114mappe2ny.klasser.Resturant;
import java.util.ArrayList;



public class EndreResturant extends AppCompatActivity implements SlettResturantDialog.DialogClickListener{


    //--------DIALOG KNAPPER TIL SLETTRESTURANTDIALOG--------
    @Override
    public void jaClick() {

        //FULLFØRER SLETTING AV RESTURANT
        fullforSlettAvResturant();
    }

    @Override
    public void neiClick() {
        return;
    }


    //--------KNAPPER--------
    private Button btnLagre,btnSlett, btnTilbake;

    //--------INPUTS--------
    private EditText EnavnResturant;
    private EditText EtlfResturant;
    private EditText EtypeResturant;

    //--------VERDIER--------
    private int valgtID;

    //--------RESTURANT OBJEKTET--------
    private Resturant valgtResturant;

    //--------DB HANDLER--------
    DBhandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endre_resturant);

        //--------KNAPPER--------
        btnLagre = (Button) findViewById(R.id.btnLagre);
        btnSlett = (Button) findViewById(R.id.btnSlett);
        btnTilbake = (Button) findViewById(R.id.btnTilbake);


        //--------INPUTS--------
        EnavnResturant = (EditText)findViewById(R.id.navnResturant);
        EtlfResturant = (EditText)findViewById(R.id.tlfResturant);
        EtypeResturant = (EditText)findViewById(R.id.typeResturant);


        //--------DB HANDLER--------
        db = new DBhandler(this);


        //--------MOTTAR INTENT OG VALGT ID--------
        Intent receivedIntent = getIntent();
        valgtID = receivedIntent.getIntExtra("id",0);

        //--------BRUKER ID TIL Å HENTE UT RESTURANT FRA DB--------
        valgtResturant = db.finnResturant(valgtID);


        //--------OUTPUT--------
        EnavnResturant.setText(valgtResturant.getNavn());
        EtlfResturant.setText(valgtResturant.getTelefon());
        EtypeResturant.setText(valgtResturant.getType());


        //--------LISTENERS--------
        //KLIKK PÅ LAGRE
        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FULLFØRER ENDRING
                fullforEndringAvResturant();
            }
        });

        //KLIKK PÅ SLETT
        btnSlett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //VISER DIALOG - SPØR OM BRUKER ER SIKKER PÅ SLETTING
                visSlettResturantDialog();
            }
        });

        //KLIKK PÅ TILBAKE
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
                Intent intent_tilbake = new Intent (EndreResturant.this, SeResturanter.class);
                startActivity(intent_tilbake);
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------



    //--------ENDRER VALGT RESTURANT--------
    private void fullforEndringAvResturant() {
        String navn = EnavnResturant.getText().toString();
        String tlf = EtlfResturant.getText().toString();
        String type = EtypeResturant.getText().toString();

        //GIR NYE VERDIER TIL RESTURANT
        valgtResturant.setNavn(navn);
        valgtResturant.setTelefon(tlf);
        valgtResturant.setType(type);


        //INPUTVALIDERING
        if(!navn.equals("") && !tlf.equals("") && !type.equals("") && tlf.matches(
                "[0-9\\+\\-\\ ]{2,15}+") && navn.matches("[a-zA-ZæøåÆØÅ\\'\\-\\ \\.]{2,50}+")
                && type.matches("[a-zA-ZæøåÆØÅ0-9\\'\\-\\ \\.]{2,50}+")){


            //OPPDATERER RESTURANT I DB
            db.oppdaterResturant(valgtResturant);


            //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
            Intent intent_tilbake = new Intent (EndreResturant.this, SeResturanter.class);
            startActivity(intent_tilbake);
            finish();

        }else{
            //INFOMELDING UT - FEIL INPUT
            toastMessage("Alle felter må fylles ut og navn og telefonnummer må være på gyldig format");
        }
    }


    //--------SLETTER VALGT RESTURANT--------
    private void fullforSlettAvResturant() {
        //SLETTER VALGT VENN FRA DB
        db.slettResturant(valgtResturant.get_ID());

        //SLETTER OGSÅ ALLE BESTILLINGER TIL RESTURANTEN
        ArrayList<Bestilling> bestillinger = db.finnAlleBestillinger();
        for (Bestilling b : bestillinger) {
            if(b.get_resturantID() == valgtResturant.get_ID()) {
                //SLETTER BESTILLING FRA DB
                db.slettBestilling(b.get_ID());

                //SLETTER OGSÅ ALLE DELTAKELSER TIL SLETTEDE BESTILLINGER
                ArrayList<Deltakelse> deltakelser = db.finnAlleDeltakelser();
                for (Deltakelse d : deltakelser) {
                    if(d.getBestillingID() == b.get_ID()) {
                        //SLETTER DELTAKELSE FRA DB
                        db.slettDeltakelse(d.getID());
                    }
                }
            }
        }

        //NULLSTILLER INPUT
        EnavnResturant.setText("");
        EtlfResturant.setText("");
        EtypeResturant.setText("");

        //INFOMELDING UT
        toastMessage("Resturant slettet fra databasen");

        //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
        Intent intent_tilbake = new Intent (EndreResturant.this, SeResturanter.class);
        startActivity(intent_tilbake);
        finish();
    }


    //-------VISER DIALOG VED SLETT KNAPP---------
    private void visSlettResturantDialog() {
        DialogFragment dialog = new SlettResturantDialog();
        dialog.show(getFragmentManager(), "Avslutt");
    }


    //-------TILBAKE KNAPP - FORHINDRER STACK---------
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