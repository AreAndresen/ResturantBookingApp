package com.skole.s304114mappe2ny;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.skole.s304114mappe2ny.ListViews.SeBestillinger;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MinService extends Service {

    DBhandler db;

    String datoIdag;

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

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        month++;

        datoIdag = day+"/"+month+"/"+year;


        ArrayList<Bestilling> alleBestillinger = db.finnAlleBestillinger();
        for(Bestilling b : alleBestillinger) {


            int index = (int) b.get_ID();

            String nokkel_MELDING = "melding"+index;

            String meldingUt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString(nokkel_MELDING,"");

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

            if(dato2.compareTo(dato4) == 0) {

                //MÅ TA MED ET TALL HER - INDEKS
                Intent intentet = new Intent(MinService.this, SeBestillinger.class);

                PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentet, 0); //100. FLAG  - PendingIntent.FLAG_UPDATE_CURRENT

                Notification notifikasjon = new NotificationCompat.Builder(this)
                        .setContentTitle("Påminnelse for bestilling i dag.")
                        .setContentText(meldingUt)

                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent).build();
                notifikasjon.flags|= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(0, notifikasjon);
                //notificationManager.notify(Unique_Integer_Number, notification);

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

}

