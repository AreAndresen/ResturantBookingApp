package com.skole.s304114mappe2ny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skole.s304114mappe2ny.klasser.Resturant;

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

                if (EnavnResturant.length() != 0 && EtlfResturant.length() != 0 && EtypeResturant.length() != 0) {

                    leggtil(navn, tlf, type);
                    //Resetter inputs
                    EnavnResturant.setText("");
                    EtlfResturant.setText("");
                    EtypeResturant.setText("");


                    Intent intent_tilbake = new Intent (LeggTilResturant.this, SeResturanter.class);
                    startActivity(intent_tilbake);
                    finish();


                } else {
                    toastMessage("Du må fylle ut alle felter. Prøv igjen.");
                }

            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeggTilResturant.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    public void leggtil(String navn, String tlf, String type) {
        //Oppretter nytt objekt
        Resturant nyResturant = new Resturant(navn, tlf, type);
        //legger til i database
        db.leggTilResturant(nyResturant);

        toastMessage("Resturant lagt til!");
        Log.d("Legg inn: ", "Resturant lagt til");
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}