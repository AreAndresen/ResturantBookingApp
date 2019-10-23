package com.skole.s304114mappe2ny;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;

public class SettPeriodiskService extends Service {

    public SettPeriodiskService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //--------HENTER DAGENS DATO--------
        Calendar c = Calendar.getInstance();

        //--------HENTER LAGRET TIDSPUNKT FRA MINNE - STANDARD 1230--------
        int time = getSharedPreferences("APP_INFO", MODE_PRIVATE).getInt("MLDTIME", 12);
        int min =  getSharedPreferences("APP_INFO", MODE_PRIVATE).getInt("MLDMIN", 30);

        Toast.makeText(this, "I BroadcastReceiver - tidspunkt: "+time+" : "+min, Toast.LENGTH_SHORT).show();


        //--------STILLER TIDEN NOTIFIKASJONEN SKAL AKTIVERES--------
        c.set(Calendar.HOUR_OF_DAY, time);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);


        //--------INTENTET TIL SERVICE--------
        Intent i = new Intent(this, MinService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //--------SETTER TIDSPUNKT OG AKTIVERER DAGLIG INTERVAL FOR NOTIFIKASJONEN--------
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);

        return super.onStartCommand(intent, flags, startId);
    }
}