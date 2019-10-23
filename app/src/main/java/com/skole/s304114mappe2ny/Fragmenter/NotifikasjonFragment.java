package com.skole.s304114mappe2ny.Fragmenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.skole.s304114mappe2ny.MinService;
import com.skole.s304114mappe2ny.R;


public class NotifikasjonFragment extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {


    //--------KNAPPER--------
    private Button btnAvbryt, btnLagre;

    //--------TEKST--------
    private TextView klokkeslettmld, klokkeslettNtf;

    //--------SWITCH--------
    private Switch notifikasjonPaAv, VarselmeldingPaAv;

    //--------VERDIER--------
    private String tid;
    private int time, minutt;
    private boolean mldAvPaa, servicePAA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasjon_fragment);


        //HENTER BOOLEAN FRA MINNET OM NOTIFIKASJON ER AKTIVERT
        servicePAA = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("SERVICEPAA",false);

        //HENTER BOOLEAN FRA MINNET OM MELDING ER AKTIVERT
        mldAvPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("MLDAVPAA",false);

        //HENTER TIDSPUNKT FRA MINNET NÅR MELDING SKAL SENDES
        tid = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString("TID","Tidspunkt ikke valgt");


        //--------KNAPPER--------
        btnLagre = findViewById(R.id.btnLagre);
        btnAvbryt = findViewById(R.id.btnAvbryt);

        //--------TEKST--------
        klokkeslettmld = findViewById(R.id.klokkeslettmld);
        klokkeslettNtf = findViewById(R.id.klokkeslettNtf);

        //--------SWITCH--------
        VarselmeldingPaAv = (Switch) findViewById(R.id.meldingPaAv);
        notifikasjonPaAv = (Switch) findViewById(R.id.notifikasjonPaAv);


        //--------SETTER LAYOUT FOR ALLE SWITCHER OSV--------
        startLayout();


        //--------LISTENERS--------
        //KLIKK PÅ NOTIFIKASJON SWITCH
        notifikasjonPaAv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //DEAKTIVERER NOTIFIKASJON OG OPPDATERER LAYPUT
                    stoppPeriodisk();
                }
                else {
                    //AKTIVERER NOTIFIKASJON
                    servicePAA = true;

                    //OPPDATERER LAYPUT
                    startLayout();
                }
            }
        });

        //KLIKK PÅ TIDSPUNKT
        klokkeslettNtf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ÅPNER TIDSPUNKT FRAGMENT OG OPPDATERER LAYOUT
                DialogFragment tidValg = new TidFragment();
                tidValg.show(getSupportFragmentManager(), "tidvalg");
            }
        });

        //KLIKK PÅ VARSELMELDING SWITCH
        VarselmeldingPaAv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //DEAKTIVERER MELDINGER
                    mldAvPaa = false;

                    //OPPDATERER LAYPUT
                    startLayout();
                } else {
                    //AKTIVERER MELDINGER
                    sendMldTillatelse();
                }
            }
        });

        //KLIKK PÅ LAGRE
        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AKTIVERER SERVICEN HVIS AKTIVERT VED LAGRING
                if(servicePAA) {
                    ServiceAuto();
                }

                //LAGRER VERDIER TIL MINNET KUN NÅR LAGRE TRYKKES
                getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("SERVICEPAA", servicePAA).apply();
                getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("MLDAVPAA", mldAvPaa).apply();
                getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putString("TID", tid).apply();
                getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("MLDTIME", time).apply();
                getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("MLDMIN", minutt).apply();

                finish();
            }
        });

        //KLIKK PÅ AVBRYT
        btnAvbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //--------SLUTT LISTENERS--------

    }//-------CREATE SLUTTER---------



    //------INNEBYGD METODE TIL TIDFRAGMENT FOR SETTING AV TID------
    @Override
    public void onTimeSet(TimePicker view, int t, int m) {

        //GIR VERDI TIL TIME OG MINUTT
        time = t;
        minutt = m;

        //BYGGER STRENGEN SOM SKAL VISE TIDSPUNKT
        tid = "Kl: " + t + ":";
        //SØRGER FOR AT DET STÅR F.EKS 10:05 ISTEDEN FOR 10:5
        if(m < 10) {
            tid += "0";
        }
        tid += m;

        //OPPDATERER LAYPUT
        startLayout();
    }


    //------METODE SOM SETTER ALLE SWITCHER OG TEXT------
    public void startLayout() {

        //SETTER SWITCHER
        VarselmeldingPaAv.setChecked(mldAvPaa);
        notifikasjonPaAv.setChecked(servicePAA);

        //DEAKTIVERER ELLER AKTIVERER MULIGHETEN TIL Å BENYTTE FELTENE
        VarselmeldingPaAv.setEnabled(servicePAA);
        klokkeslettmld.setEnabled(servicePAA);
        klokkeslettNtf.setEnabled(servicePAA);

        //SETTER TIDSPUNKT FOR NOTIFIKASJON OG MELDING
        //NOTIFIKASJON
        if(servicePAA) {
            klokkeslettNtf.setText(tid);
        }
        else {
            klokkeslettNtf.setText("Notifikasjon deaktivert");
        }
        //MELDING
        if(mldAvPaa) {
            klokkeslettmld.setText(tid);
        }
        else {
            klokkeslettmld.setText("Ingen varselmelding");
        }
    }


    //-------METODE SOM STARTER SERVICE------
    public void ServiceAuto() {
        Intent intent = new Intent();
        intent.setAction("com.skole.s304114mappe2ny");
        sendBroadcast(intent);
    }

    //-------METODE SOM STOPPER SERVICE------
    public void stoppPeriodisk() {
        Intent i = new Intent(this, MinService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if(alarm!= null) {
            alarm.cancel(pintent);
        }

        //DEAKTIVERER SERVICE OG MELDINGER
        servicePAA = false;
        mldAvPaa = false;

        //OPPDATERER LAYPUT
        startLayout();
    }



    //-------KONTROLLERER TILLATELSER-------
    public void sendMldTillatelse() {
        int tillatelseSjekkSend = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS);
        int tillatelsesjekkLesState = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);

        if(tillatelseSjekkSend == PackageManager.PERMISSION_GRANTED && tillatelsesjekkLesState == PackageManager.PERMISSION_GRANTED) {
            mldAvPaa = true;

            //OPPDATERER LAYPUT
            startLayout();
        }
        else {
            Toast.makeText(this, "Ikke tillatelse til å sende sms", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }




    //HENTER VARIABLER FRA MINNET
    @Override
    protected void onResume(){
        super.onResume();

        servicePAA = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("SERVICEPAA",false);
        mldAvPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("MLDAVPAA",false);
        tid = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString("TID","Tidspunkt ikke valgt");
        time = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("MLDTIME",17);
        minutt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("MLDMIN",0);
    }
}


