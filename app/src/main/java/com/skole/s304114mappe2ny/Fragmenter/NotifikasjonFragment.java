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

import java.text.DateFormat;
import java.util.Calendar;

public class NotifikasjonFragment  extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {


    private TextView klokkeslett;

    private Button btnTilbake;

    private Switch notifikasjonPaAv;
    private int avPaaVerdi;

    private String tid;

    private int time, minutt; //brukes til overføring i minne og når melding sendes

    private boolean avPaa, servicePAA;

    Button buttonTimePicker, buttonCancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        servicePAA = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("SERVICEPAA",true);
        avPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("AVPAA",false);
        tid = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString("TID","");

        setContentView(R.layout.activity_notifikasjon_fragment);

        notifikasjonPaAv = (Switch) findViewById(R.id.notifikasjonPaAv);


        klokkeslett = findViewById(R.id.klokkeslett);
        if(avPaa ) {
            klokkeslett.setText(tid);


        }
        //sørger for at switch er koblet ut om service er slått av
        if(servicePAA) {
            //starter service hver gang tlfen starter opp
            ServiceAuto();
            notifikasjonPaAv.setEnabled(servicePAA);

            notifikasjonPaAv.setEnabled(true);
        }
        else{
            notifikasjonPaAv.setEnabled(false);
        }



        Toast.makeText(getApplicationContext(), servicePAA+" ", Toast.LENGTH_LONG).show();
        //lagrer om switch er aktivert for melding eller ikke
        SharedPreferences sharedPreferences = getSharedPreferences("APP_INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //notifikasjonPaAv = (Switch) findViewById(R.id.notifikasjonPaAv);
        //notifikasjonPaAv.setEnabled(servicePAA);
        notifikasjonPaAv.setChecked(avPaa);

        //avPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("AVPAA",true);

        notifikasjonPaAv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    avPaa = false;
                    klokkeslett.setText("Ingen varselmelding");
                } else {
                    avPaa = true;
                    DialogFragment timePicker = new TimePickerFragment();

                    timePicker.show(getFragmentManager(), "time picker");
                    klokkeslett.setText(tid);
                }
            }
        });

        editor.putBoolean("SERVICEPAA", servicePAA); //lagrer nøkkel med nøkkel string
        editor.putBoolean("AVPAA", avPaa); //lagrer nøkkel med nøkkel string

        editor.putInt("MLDTIME", time);
        editor.putInt("MLDMIN", minutt);

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
        //Calendar c = Calendar.getInstance();
        //c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        //c.set(Calendar.MINUTE, minute);
        //c.set(Calendar.SECOND, 0);

        tid = "Kl: " + hourOfDay + ":";
        //sørger for at det står f.eks 10:05 isteden for 10:5
        if(minute < 10) {
            tid += "0";
        }
        tid += minute;
        klokkeslett.setText(tid);

        //updateTimeText(c);
        //startAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Sendes: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        klokkeslett.setText(timeText);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }



    public void startService(View v) {
        /*Intentintent= newIntent(this, MinService.class);
        this.startService(intent);*/

        servicePAA = true;
        notifikasjonPaAv.setEnabled(servicePAA);

        ServiceAuto();

        //TIL MinBroadcastReciver
        //sendBroadcast(intent);
    }


    public void ServiceAuto() {
        Intent intent = new Intent();
        intent.setAction("com.skole.s304114mappe2ny");
        sendBroadcast(intent);

        //gir tillatelse til å sende melding
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},0);
    }


    public void stoppPeriodisk(View v) {
        Intent i = new Intent(this, MinService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if(alarm!= null) {
            alarm.cancel(pintent);
        }
        servicePAA = false;
        notifikasjonPaAv.setEnabled(servicePAA);
        klokkeslett.setText("Ingen varselmelding");
        avPaa = false;
        notifikasjonPaAv.setChecked(avPaa);

    }


    //-------LAGRER SPRÅKENRDRING VED RETUR TIL MAIN-------
    @Override
    protected void onPause(){
        super.onPause();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("SERVICEPAA", servicePAA).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putBoolean("AVPAA", avPaa).apply();
        getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putString("TID", tid).apply();

    }

    @Override
    protected void onResume(){
        super.onResume();

        servicePAA = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("SERVICEPAA",false);
        avPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("AVPAA",false);
        tid = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString("TID","");
    }
}


