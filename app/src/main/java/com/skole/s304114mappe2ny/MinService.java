package com.skole.s304114mappe2ny;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.skole.s304114mappe2ny.ListViews.SeBestillinger;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Deltakelse;
import com.skole.s304114mappe2ny.klasser.Venn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MinService extends Service {

    DBhandler db;

    String datoIdag;

    ArrayList<Integer> sendteMeldinger = new ArrayList<>();

    public MinService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I MinService", Toast.LENGTH_SHORT).show();


        db = new DBhandler(this);

        //henter dagens dato i riktig format til sammenligning av det som ligger i db
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        month++;

        datoIdag = day+"/"+month+"/"+year;

        //int antall = 0;


        ArrayList<Bestilling> alleBestillinger = db.finnAlleBestillinger();
        for(Bestilling b : alleBestillinger) {

            int index = (int) b.get_ID();

            String nokkel_MELDING = "melding"+index;

            String meldingUt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString(nokkel_MELDING,"");


            //Samler alle venner til aktuell bestilling
            ArrayList<Venn> vennerUtmelding = new ArrayList<>();

            ArrayList<Deltakelse> alleDeltakelser = db.finnAlleDeltakelser();
            for(Deltakelse d : alleDeltakelser) {
                if(d.getBestillingID() == b.get_ID()) {
                        ArrayList<Venn> alleVenner = db.finnAlleVenner();
                        for(Venn v : alleVenner) {
                            if(v.getID() == d.getVennID()) {
                               vennerUtmelding.add(v);
                            }
                        }
                }
            }

            String dato1 = b.getDato();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dato2 = null;
            Date dato4 = null;

            try {
                dato2 = sdf.parse(dato1);
                dato4 = sdf.parse(datoIdag);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if((dato2.compareTo(dato4) == 0)) { //hvis datoen er i dag

            //while (dato2.compareTo(dato4) == 0) {


                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                //MÅ TA MED ET TALL HER - INDEKS
                Intent intentet = new Intent(MinService.this, SeBestillinger.class);

                PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentet, 0); //100. FLAG  - PendingIntent.FLAG_UPDATE_CURRENT

                Notification notifikasjon = new NotificationCompat.Builder(this)
                        .setContentTitle("Påminnelse")
                        .setContentText(meldingUt)

                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent).build();
                notifikasjon.flags|= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(m, notifikasjon);
                //notificationManager.notify(Unique_Integer_Number, notification);


                //MÅ HA EN HVIS NOTIFIKASJON - MELDING ER SKRUDD PÅ HER IF TRUE
                boolean avPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("AVPAA",true);

                if(avPaa) {
                    //sender melding til alle venner som er i deltakelsen
                    SmsManager minSmsManager = SmsManager.getDefault();
                    for(Venn v : vennerUtmelding) {
                        minSmsManager.sendTextMessage(v.getTelefon(), null, meldingUt, null, null);
                    }
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

}

