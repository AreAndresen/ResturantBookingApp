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
import com.skole.s304114mappe2ny.ListViews.SeVenner;
import com.skole.s304114mappe2ny.SlettDialoger.SlettVennDialog;
import com.skole.s304114mappe2ny.klasser.Deltakelse;
import com.skole.s304114mappe2ny.klasser.Venn;
import java.util.ArrayList;


public class EndreVenn extends AppCompatActivity implements SlettVennDialog.DialogClickListener{

    //--------DIALOG KNAPPER TIL SLETTVENNDIALOG--------
    @Override
    public void jaClick() {

        //FULLFØRER SLETTING AV VENN
        fullforSlettAvVenn();
    }

    @Override
    public void neiClick() {
        return;
    }


    //--------KNAPPER--------
    private Button btnLagre,btnSlett, btnTilbake;

    //--------INPUTS--------
    private EditText EnavnVenn;
    private EditText EtlfVenn;

    //--------VERDIER--------
    private int valgtID;

    //--------VENN OBJEKTET--------
    private Venn valgtVenn;

    //--------DB HANDLER--------
    DBhandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endre_venn);

        //--------KNAPPER--------
        btnLagre = (Button) findViewById(R.id.btnLagre);
        btnSlett = (Button) findViewById(R.id.btnSlett);
        btnTilbake = (Button) findViewById(R.id.btnTilbake);


        //--------INPUTS--------
        EnavnVenn = (EditText)findViewById(R.id.navnVenn);
        EtlfVenn = (EditText)findViewById(R.id.tlfVenn);


        //--------DB HANDLER--------
        db = new DBhandler(this);


        //--------MOTTAR INTENT OG VALGT ID--------
        Intent motattIntent = getIntent();
        valgtID = motattIntent.getIntExtra("id",0);

        //--------BRUKER ID TIL Å HENTE UT VENN FRA DB--------
        valgtVenn = db.finnVenn(valgtID);


        //--------OUTPUT--------
        EnavnVenn.setText(valgtVenn.getNavn());
        EtlfVenn.setText(valgtVenn.getTelefon());


        //--------LISTENERS--------
        //KLIKK PÅ LAGRE
        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FULLFØRER ENDRING
                fullforEndringAvVenn();
            }
        });

        //KLIKK PÅ SLETT
        btnSlett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VISER DIALOG - SPØR OM BRUKER ER SIKKER PÅ SLETTING
                visSlettVennDialog();
            }
        });

        //KLIKK PÅ TILBAKE
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
                Intent intent_tilbake = new Intent (EndreVenn.this, SeVenner.class);
                startActivity(intent_tilbake);
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------


    //--------ENDRER VALGT VENN--------
    private void fullforEndringAvVenn() {
        String navn = EnavnVenn.getText().toString();
        String tlf = EtlfVenn.getText().toString();

        //GIR NYE VERDIER TIL VENN
        valgtVenn.setNavn(navn);
        valgtVenn.setTelefon(tlf);


        //INPUTVALIDERING
        if (!navn.equals("") && !tlf.equals("") && tlf.matches("[0-9\\+\\-\\ ]{2,15}+")
                && navn.matches("[a-zA-ZæøåÆØÅ\\'\\-\\ \\.]{2,40}+")) {


            //OPPDATERER VENN I DB
            db.oppdaterVenn(valgtVenn);


            //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
            Intent intentet = new Intent(EndreVenn.this, SeVenner.class);
            startActivity(intentet);
            finish();

        }
        else {
            //INFOMELDING UT - FEIL INPUT
            toastMessage("Alle felter må fylles ut og navn og telefonnummer må være på gyldig format");
        }
    }


    //--------SLETTER VALGT VENN--------
    private void fullforSlettAvVenn() {
        //SLETTER VALGT VENN FRA DB
        db.slettVenn(valgtVenn.getID());

        //SLETTR OGSÅ ALLE DELTAKELSER TIL DENNE VENNEN
        ArrayList<Deltakelse> deltakelser = db.finnAlleDeltakelser();
        for (Deltakelse d : deltakelser) {
            if(d.getVennID() == valgtVenn.getID()) {
                db.slettDeltakelse(d.getID());
            }
        }

        //NULLSTILLER INPUT
        EnavnVenn.setText("");
        EtlfVenn.setText("");

        //INFOMELDING UT
        toastMessage("Venn slettet fra databasen");

        //VIEW OPPDATERES FORTLØPENDE - FORHINDRER STACK
        Intent intentet = new Intent (EndreVenn.this, SeVenner.class);
        startActivity(intentet);
        finish();
    }


    //-------VISER DIALOG VED SLETT KNAPP---------
    private void visSlettVennDialog() {
        DialogFragment dialog = new SlettVennDialog();
        dialog.show(getFragmentManager(), "Slett");
    }


    //-------TILBAKE KNAPP - FORHINDRER STACK---------
    @Override
    public void onBackPressed() {
        Intent intent_tilbake = new Intent (EndreVenn.this, SeVenner.class);
        startActivity(intent_tilbake);
        finish();
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}