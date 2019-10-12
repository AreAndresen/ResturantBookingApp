package com.skole.s304114mappe2ny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoFragment;
import com.skole.s304114mappe2ny.ListViews.SeBestillinger;


public class Resultat extends AppCompatActivity {


    private String meldingUt;
    private static final String NOKKEL_MELDINGUT = "meldingUt_nokkel";

    TextView meldingT;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.se_bestilling_melding);

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        meldingT = findViewById(R.id.melding);


        //Intent editScreenIntent = new Intent(Resultat.this, SeBestillingsInfoFragment.class);


        //prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefs = getSharedPreferences("APP_INFO", MODE_PRIVATE);
        meldingUt = prefs.getString(NOKKEL_MELDINGUT,"");



        meldingT.setText(meldingUt);


        //String utmelding  = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString(NOKKEL_MELDINGUT,"");

        //editScreenIntent.putExtra("id",ID);
        //startActivity(editScreenIntent);
        //finish();

    }
}

