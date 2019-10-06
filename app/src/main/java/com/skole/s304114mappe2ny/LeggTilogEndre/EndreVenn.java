package com.skole.s304114mappe2ny.LeggTilogEndre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.skole.s304114mappe2ny.DBhandler;
import com.skole.s304114mappe2ny.R;
import com.skole.s304114mappe2ny.ListViews.SeVenner;
import com.skole.s304114mappe2ny.klasser.Venn;

/**
 * Created by User on 2/28/2017.
 */

public class EndreVenn extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete;


    private EditText EnavnVenn;
    private EditText EtlfVenn;

    DBhandler db;

    private String valgtNavn, valgtTlf;
    private int valgtID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endre_venn);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);


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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String item = editable_item.getText().toString();

                String navn = EnavnVenn.getText().toString();
                String tlf = EtlfVenn.getText().toString();

                Venn venn = db.finnVenn(valgtID); //manuell her
                venn.setNavn(navn);
                venn.setTelefon(tlf);
                //Resturant oppdatertResturant = new Resturant(navn,tlf, type);;


                if(!navn.equals("") && !tlf.equals("")){
                    //mDatabaseHelper.updateName(item,selectedID,selectedName);

                    db.oppdaterVenn(venn);

                    //gjørs så viewet oppdaterer fortløpende
                    Intent intent_tilbake = new Intent (EndreVenn.this, SeVenner.class);
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

                Venn slettVenn = db.finnVenn(valgtID);

                db.slettVenn(slettVenn.getID());

                EnavnVenn.setText("");
                EtlfVenn.setText("");

                //gjørs så viewet oppdaterer fortløpende
                Intent intent_tilbake = new Intent (EndreVenn.this, SeVenner.class);
                startActivity(intent_tilbake);
                finish();

                toastMessage("removed from database");
            }
        });

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
