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

import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoFragment;
import com.skole.s304114mappe2ny.ListViews.SeBestillinger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MinService extends Service {

    /*
    Når MinService kjører vil jeg at den skal gi en Notification.
    Når det klikkes på denne vil jeg at aktiviteten Resultat.java skal startes opp.
    Resulat.java har layout fil resultat.xml som bare setter bakgrunn til blå
     */
    private static final String NOKKEL_MELDINGUT = "meldingUt_nokkel";
    DBhandler db;

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

        db = new DBhandler(this);
        Integer index = db.finnAlleBestillinger().size();
        //index--;

        String NOKKEL = index+""; //løpende nøkkel
        String nokkel_MELDING = "melding"+index;

        //Henter meldingen fra MINNE
        String meldingUt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString(nokkel_MELDING,"");

        //henter og finner indeks
        int indeks = getSharedPreferences("APP_INFO",MODE_PRIVATE).getInt(NOKKEL,2);



        //sender oss til RESULTAT.JAVA
        //Intent inten = new Intent(this, Resultat.class);

        String valid_until = db.finnBestilling(indeks).getDato();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //if (System.currentTimeMillis() == strDate.getTime()) {
        if (1 == 1) {

            //MÅ TA MED ET TALL HER - INDEKS
            Intent intentet = new Intent(MinService.this, SeBestillinger.class);

            //intentet.putExtra("id",indeks++);
            //startActivity(intentet);
            //finish(); //unngår å legge på stack


            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentet, 0);
            Notification notifikasjon = new NotificationCompat.Builder(this)
                    .setContentTitle("Påminnelse for bestilling i dag.")
                    .setContentText(meldingUt)


                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent).build();
            notifikasjon.flags|= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notifikasjon);



        }
        return super.onStartCommand(intent, flags, startId);



    }
}

