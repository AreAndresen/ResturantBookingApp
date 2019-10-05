package com.skole.s304114mappe2ny;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.skole.s304114mappe2ny.klasser.Resturant;

/**
 * Created by User on 2/28/2017.
 */

public class EndreResturant extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete;


    private EditText EnavnResturant;
    private EditText EtlfResturant;
    private EditText EtypeResturant;

    DBhandler db;

    private String valgtNavn, valgtTlf, valgtType;
    private int valgtID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endre_resturant);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);


        EnavnResturant = (EditText)findViewById(R.id.navnResturant);
        EtlfResturant = (EditText)findViewById(R.id.tlfResturant);
        EtypeResturant = (EditText)findViewById(R.id.typeResturant);

        db = new DBhandler(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        valgtID = receivedIntent.getIntExtra("id",0); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        valgtNavn = receivedIntent.getStringExtra("name");

        //now get the name we passed as an extra
        valgtTlf = receivedIntent.getStringExtra("tlf");

        //now get the name we passed as an extra
        valgtType = receivedIntent.getStringExtra("type");


        //set the text to show the current selected name
        EnavnResturant.setText(valgtNavn);
        EtlfResturant.setText(valgtTlf);
        EtypeResturant.setText(valgtType);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String item = editable_item.getText().toString();

                String navn = EnavnResturant.getText().toString();
                String tlf = EtlfResturant.getText().toString();
                String type = EtypeResturant.getText().toString();


                Resturant resturant = db.finnResturant(valgtID); //manuell her
                resturant.setNavn(navn);
                resturant.setTelefon(tlf);
                resturant.setType(type);
                //Resturant oppdatertResturant = new Resturant(navn,tlf, type);;


                if(!navn.equals("") && !tlf.equals("") && !type.equals("")){
                    //mDatabaseHelper.updateName(item,selectedID,selectedName);

                    db.oppdaterResturant(resturant);

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

                Resturant slettResturant = db.finnResturant(valgtID);

                db.slettResturant(slettResturant.get_ID());

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

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
