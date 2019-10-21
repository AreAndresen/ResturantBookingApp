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

    @Override
    public void jaClick() {
        fullforSlettAvVenn();
    }

    @Override
    public void neiClick() {
        return;
    }

    private static final String TAG = "EditDataActivity";

    private Button btnLagre,btnSlett, btnTilbake;


    private EditText EnavnVenn;
    private EditText EtlfVenn;

    DBhandler db;

    private String valgtNavn, valgtTlf;
    private int valgtID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endre_venn);

        btnLagre = (Button) findViewById(R.id.btnLagre);
        btnSlett = (Button) findViewById(R.id.btnSlett);
        btnTilbake = (Button) findViewById(R.id.btnTilbake);


        EnavnVenn = (EditText)findViewById(R.id.navnVenn);
        EtlfVenn = (EditText)findViewById(R.id.tlfVenn);

        db = new DBhandler(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        valgtID = receivedIntent.getIntExtra("id",0); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        valgtNavn = receivedIntent.getStringExtra("name");

        //now get the name we passed as an extra
        valgtTlf = receivedIntent.getStringExtra("tlf");



        //set the text to show the current selected name
        EnavnVenn.setText(valgtNavn);
        EtlfVenn.setText(valgtTlf);

        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String item = editable_item.getText().toString();

                String navn = EnavnVenn.getText().toString();
                String tlf = EtlfVenn.getText().toString();

                Venn venn = db.finnVenn(valgtID); //manuell her
                venn.setNavn(navn);
                venn.setTelefon(tlf);
                //Resturant oppdatertResturant = new Resturant(navn,tlf, type);;


                if(!navn.equals("") && !tlf.equals("") && tlf.matches(
                        "[0-9\\+\\-\\ ]{2,15}+") && navn.matches("[a-zA-ZæøåÆØÅ\\'\\-\\ \\.]{2,50}+")){
                    //mDatabaseHelper.updateName(item,selectedID,selectedName);

                    db.oppdaterVenn(venn);

                    //gjørs så viewet oppdaterer fortløpende
                    Intent intent_tilbake = new Intent (EndreVenn.this, SeVenner.class);
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

                visSlettVennDialog();
            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gjørs så viewet oppdaterer fortløpende
                Intent intent_tilbake = new Intent (EndreVenn.this, SeVenner.class);
                startActivity(intent_tilbake);
                finish();
            }
        });


    }

    //slettmetode av venn
    private void fullforSlettAvVenn() {
        Venn slettVenn = db.finnVenn(valgtID);
        db.slettVenn(slettVenn.getID());

        //sletter også alle deltakelser til slettet venn
        ArrayList<Deltakelse> deltakelser = db.finnAlleDeltakelser();
        for (Deltakelse d : deltakelser) {
            if(d.getVennID() == slettVenn.getID()) {
                db.slettDeltakelse(d.getID());
            }
        }


        EnavnVenn.setText("");
        EtlfVenn.setText("");

        //gjørs så viewet oppdaterer fortløpende
        Intent intent_tilbake = new Intent (EndreVenn.this, SeVenner.class);
        startActivity(intent_tilbake);
        finish();

        toastMessage("removed from database");
    }


    //-------VISER DIALOG VED SLETT KNAPP---------
    private void visSlettVennDialog() {
        DialogFragment dialog = new SlettVennDialog();
        dialog.show(getFragmentManager(), "Avslutt");
    }



    @Override
    public void onBackPressed() {
        Intent intent_tilbake = new Intent (EndreVenn.this, SeVenner.class);
        startActivity(intent_tilbake);
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

