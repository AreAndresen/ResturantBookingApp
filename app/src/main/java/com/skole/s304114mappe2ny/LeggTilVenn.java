package com.skole.s304114mappe2ny;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skole.s304114mappe2ny.klasser.Venn;

public class LeggTilVenn extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DBhandler db;

    private EditText EnavnVenn;
    private EditText EtlfVenn;

    private Button btnAdd, btnTilbake;
    //private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legg_til_venn);

        EnavnVenn = (EditText)findViewById(R.id.navnVenn);
        EtlfVenn = (EditText)findViewById(R.id.tlfVenn);


        btnAdd = (Button) findViewById(R.id.btnLeggTil);
        btnTilbake = (Button) findViewById(R.id.btnTilbake);
        db = new DBhandler(this);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String newEntry = editText.getText().toString();

                String navn = EnavnVenn.getText().toString();
                String tlf = EtlfVenn.getText().toString();

                if (EnavnVenn.length() != 0 && EtlfVenn.length() != 0) {

                    leggtil(navn, tlf);


                } else {
                    toastMessage("Du må fylle ut alle felter. Prøv igjen.");
                }
            }
        });

        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeggTilVenn.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    public void leggtil(String navn, String tlf) {
        //legger til venn
        Venn nyVenn = new Venn(navn, tlf);
        //legger til i database
        db.leggTilVenn(nyVenn);

        //Resetter inputs
        EnavnVenn.setText("");
        EtlfVenn.setText("");

        toastMessage("Venn lagt til!");
        Log.d("Legg inn: ", "Venn lagt til");


        Intent intent_tilbake = new Intent (LeggTilVenn.this, SeVenner.class);
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

