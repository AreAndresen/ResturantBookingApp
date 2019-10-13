package com.skole.s304114mappe2ny;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

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

        java.util.Calendar cal = Calendar.getInstance();

        //setter tiden notifikasjonen skal gå
        cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);

        //TIL MinService
        Intent i = new Intent(this, MinService.class);

        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0); //100. FLAG  - PendingIntent.FLAG_UPDATE_CURRENT

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 6 * 100, pintent); //6 * 100 AlarmManager.INTERVAL_DAY

        return super.onStartCommand(intent, flags, startId);
    }
}