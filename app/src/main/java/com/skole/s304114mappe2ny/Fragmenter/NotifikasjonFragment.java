package com.skole.s304114mappe2ny.Fragmenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.AlarmManager;
import android.app.DialogFragment;
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
import com.skole.s304114mappe2ny.TimePickerFragment;


public class NotifikasjonFragment  extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView klokkeslettmld, klokkeslettNtf;
    private Button btnTilbake;
    private Switch notifikasjonPaAv, VarselmeldingPaAv;
    private String tid;
    private int time, minutt; //brukes til overføring i minne og når melding sendes
    private boolean mldAvPaa, servicePAA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        servicePAA = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("SERVICEPAA",true);
        mldAvPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("MLDAVPAA",false);
        tid = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString("TID","");

        setContentView(R.layout.activity_notifikasjon_fragment);

        VarselmeldingPaAv = (Switch) findViewById(R.id.meldingPaAv);
        notifikasjonPaAv = (Switch) findViewById(R.id.notifikasjonPaAv);
        klokkeslettmld = findViewById(R.id.klokkeslettmld);
        klokkeslettNtf = findViewById(R.id.klokkeslettNtf);


        if(mldAvPaa) {
            klokkeslettmld.setText(tid);
        }

        //sørger for at switch er koblet ut om service er slått av
        if(servicePAA) {
            //starter service hver gang tlfen starter opp
            startLayout();
            //ServiceAuto();
        }
        else{
            VarselmeldingPaAv.setEnabled(servicePAA);
        }


        klokkeslettNtf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(), "time picker");;
            }
        });

        Toast.makeText(getApplicationContext(), servicePAA+" ", Toast.LENGTH_LONG).show();

        notifikasjonPaAv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    stoppPeriodisk();

                } else {
                    startLayout();
                }
            }
        });


        VarselmeldingPaAv.setChecked(mldAvPaa);
        VarselmeldingPaAv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    mldAvPaa = false;
                    klokkeslettmld.setText("Ingen varselmelding");
                } else {
                    mldAvPaa = true;
                    startLayout();

                }
            }
        });

        /*getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("SERVICEPAA", servicePAA).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("MLDAVPAA", mldAvPaa).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putString("TID", tid).apply();*/


        btnTilbake = findViewById(R.id.btnTilbake);
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aktiverer service hvis aktivert
                if(servicePAA) {
                    ServiceAuto();
                }
                finish();
            }
        });
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        /*getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("MLDTIME", hourOfDay).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("MLDMIN", minute).apply();

        time = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("MLDTIME",17);
        minutt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("MLDMIN",0);*/

        time = hourOfDay;
        minutt = minute;

        tid = "Kl: " + hourOfDay + ":";
        //sørger for at det står f.eks 10:05 isteden for 10:5
        if(minute < 10) {
            tid += "0";
        }
        tid += minute;
        //setter tidspunktet for notifikasjonen og melding
        klokkeslettmld.setText(tid);
        klokkeslettNtf.setText(tid);
    }


    //------METODE SOM SETTER ALLE SWITCHER OG TEXT
    public void startLayout() {
        servicePAA = true;
        VarselmeldingPaAv.setEnabled(servicePAA);
        klokkeslettmld.setEnabled(servicePAA);
        klokkeslettNtf.setEnabled(servicePAA);
        notifikasjonPaAv.setChecked(servicePAA);

        klokkeslettNtf.setText(tid);

        if(mldAvPaa) {
            klokkeslettmld.setText(tid);
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
        servicePAA = false;
        mldAvPaa = false;

        VarselmeldingPaAv.setEnabled(servicePAA);
        VarselmeldingPaAv.setChecked(mldAvPaa);

        klokkeslettNtf.setEnabled(servicePAA);
        klokkeslettmld.setEnabled(servicePAA);

        klokkeslettNtf.setText("Notifikasjon deaktivert");
        klokkeslettmld.setText("Ingen varselmelding");
    }


    //-------LAGRER SPRÅKENRDRING VED RETUR TIL MAIN-------
    @Override
    protected void onPause(){
        super.onPause();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("SERVICEPAA", servicePAA).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("MLDAVPAA", mldAvPaa).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putString("TID", tid).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("MLDTIME", time).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("MLDMIN", minutt).apply();

    }

    @Override
    protected void onResume(){
        super.onResume();

        servicePAA = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("SERVICEPAA",false);
        mldAvPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("MLDAVPAA",false);
        tid = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString("TID","");
        time = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("MLDTIME",17);
        minutt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("MLDMIN",0);

    }
}


