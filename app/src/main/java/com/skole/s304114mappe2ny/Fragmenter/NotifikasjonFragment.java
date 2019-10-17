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

    private Switch notifikasjonPaAv, meldingPaAv;

    private String tid;

    private int time, minutt; //brukes til overføring i minne og når melding sendes

    private boolean avPaa, servicePAA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        servicePAA = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("SERVICEPAA",true);
        avPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("AVPAA",false);
        tid = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString("TID","");


        setContentView(R.layout.activity_notifikasjon_fragment);

        meldingPaAv = (Switch) findViewById(R.id.meldingPaAv);
        notifikasjonPaAv = (Switch) findViewById(R.id.notifikasjonPaAv);


        klokkeslettmld = findViewById(R.id.klokkeslettmld);
        klokkeslettNtf = findViewById(R.id.klokkeslettNtf);

        if(avPaa) {
            klokkeslettmld.setText(tid);
        }
        //sørger for at switch er koblet ut om service er slått av
        if(servicePAA) {
            //starter service hver gang tlfen starter opp
            startService();
            ServiceAuto();
        }
        else{
            meldingPaAv.setEnabled(servicePAA);

        }


        klokkeslettNtf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();

                timePicker.show(getFragmentManager(), "time picker");
                ServiceAuto();
            }
        });
        /*klokkeslettmld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();

                timePicker.show(getFragmentManager(), "time picker");
                ServiceAuto();
            }
        });*/


        Toast.makeText(getApplicationContext(), servicePAA+" ", Toast.LENGTH_LONG).show();
        //lagrer om switch er aktivert for melding eller ikke
        SharedPreferences sharedPreferences = getSharedPreferences("APP_INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        meldingPaAv.setChecked(avPaa);

        //avPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("AVPAA",true);
        notifikasjonPaAv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {

                    stoppPeriodisk();

                } else {
                    startService();
                    ServiceAuto();
                }
            }
        });


        meldingPaAv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    avPaa = false;
                    klokkeslettmld.setText("Ingen varselmelding");
                } else {
                    startService();

                    //DialogFragment timePicker = new TimePickerFragment();

                    //timePicker.show(getFragmentManager(), "time picker");

                    ServiceAuto();
                    avPaa = true;
                }
            }
        });

        editor.putBoolean("SERVICEPAA", servicePAA); //lagrer nøkkel med nøkkel string
        editor.putBoolean("AVPAA", avPaa); //lagrer nøkkel med nøkkel string


        editor.putString("TID", tid);

        //editor.putInt("MELDINGAVPAAVERDI", avPaaVerdi); //lagrer nøkkel med nøkkel string
        editor.apply(); //apply



        btnTilbake = findViewById(R.id.btnTilbake);
        btnTilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        SharedPreferences sharedPreferences = getSharedPreferences("APP_INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        time = hourOfDay;
        minutt = minute;

        editor.putInt("MLDTIME", time);
        editor.putInt("MLDMIN", minutt);


        tid = "Kl: " + hourOfDay + ":";
        //sørger for at det står f.eks 10:05 isteden for 10:5
        if(minute < 10) {
            tid += "0";
        }
        tid += minute;
        klokkeslettmld.setText(tid);
        klokkeslettNtf.setText(tid);
    }

    public void startService() {
        /*Intentintent= newIntent(this, MinService.class);
        this.startService(intent);*/

        klokkeslettmld.setText(tid);

        servicePAA = true;
        meldingPaAv.setEnabled(servicePAA);
        klokkeslettNtf.setEnabled(servicePAA);
        notifikasjonPaAv.setChecked(servicePAA);
        klokkeslettNtf.setText(tid);
    }


    public void ServiceAuto() {
        Intent intent = new Intent();
        intent.setAction("com.skole.s304114mappe2ny");
        sendBroadcast(intent);

        //gir tillatelse til å sende melding
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},0);
    }


    public void stoppPeriodisk() {
        Intent i = new Intent(this, MinService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if(alarm!= null) {
            alarm.cancel(pintent);
        }
        servicePAA = false;
        meldingPaAv.setEnabled(servicePAA);
        klokkeslettNtf.setEnabled(servicePAA);

        klokkeslettNtf.setText("Notifikasjon deaktivert");
        klokkeslettmld.setText("Ingen varselmelding");
        avPaa = false;
        meldingPaAv.setChecked(avPaa);

    }


    //-------LAGRER SPRÅKENRDRING VED RETUR TIL MAIN-------
    @Override
    protected void onPause(){
        super.onPause();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("SERVICEPAA", servicePAA).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("AVPAA", avPaa).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putString("TID", tid).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("MLDTIME", time).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("MLDMIN", minutt).apply();

    }

    @Override
    protected void onResume(){
        super.onResume();

        servicePAA = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("SERVICEPAA",false);
        avPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("AVPAA",false);
        tid = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString("TID","");
        time = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("MLDTIME",17);
        minutt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt("MLDMIN",0);

    }
}


