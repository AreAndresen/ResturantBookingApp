package com.skole.s304114mappe2ny;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MinService extends Service {

    /*
    Når MinService kjører vil jeg at den skal gi en Notification.
    Når det klikkes på denne vil jeg at aktiviteten Resultat.java skal startes opp.
    Resulat.java har layout fil resultat.xml som bare setter bakgrunn til blå
     */
    private static final String NOKKEL_MELDINGUT = "meldingUt_nokkel";

    public MinService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I MinService", Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);


        String meldingUt = prefs.getString(NOKKEL_MELDINGUT, "Skal være en melding her");


        Intent inten = new Intent(this, Resultat.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, inten, 0);
        Notification notifikasjon = new NotificationCompat.Builder(this)
                .setContentTitle("MinNotifikasjon")
                .setContentText(meldingUt)



                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).build();
        notifikasjon.flags|= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notifikasjon);


        return super.onStartCommand(intent, flags, startId);}
}

