package com.skole.s304114mappe2ny;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.skole.s304114mappe2ny.Fragmenter.SeBestillingsInfoFragment;
import com.skole.s304114mappe2ny.klasser.Bestilling;
import com.skole.s304114mappe2ny.klasser.Deltakelse;
import com.skole.s304114mappe2ny.klasser.Venn;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MinService extends Service {

    //--------DB HANDLER--------
    DBhandler db;

    //--------VERDIER--------
    String datoIdag;


    public MinService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //--------DB HANDLER--------
        db = new DBhandler(this);


        //--------HENTER ALLE BESTILLINGER FRA DB--------
        ArrayList<Bestilling> alleBestillinger = db.finnAlleBestillinger();


        //--------GÅR GJENNOM ALLE BESTILLINGER--------
        for(Bestilling b : alleBestillinger) {

            //HENTER INDEKS
            int index = (int) b.get_ID();

            //BENYTTER INDEKS TIL Å GJENNSKAPE NØKKELEN TIL MELDINGEN I MINNE
            String nokkel_MELDING = "melding"+index;

            //HENTER UT MELDINGEN FRA MINNE MED DEN GJENSKAPTE NØKKELEN TIL MELDINGEN
            String meldingUt = getSharedPreferences("APP_INFO",MODE_PRIVATE).getString(nokkel_MELDING,"");


            //ARRAY FOR Å SAMLE ALLE VENNER I BESTILLINGEN SOM SKAL FÅ MELDING
            ArrayList<Venn> vennerUtmelding = new ArrayList<>();


            //HENTER ALLE DELTAKLSER FRA DB
            ArrayList<Deltakelse> alleDeltakelser = db.finnAlleDeltakelser();

            //FINNER VENNER/DELTAKERE FRA BESTILLINGEN OG LEGGER VENNENE I VENNERUTMELDING ARRAYET
            for(Deltakelse d : alleDeltakelser) {
                if(d.getBestillingID() == b.get_ID()) {

                    ArrayList<Venn> alleVenner = db.finnAlleVenner();

                        for(Venn v : alleVenner) {
                            if(v.getID() == d.getVennID()) {
                                //LEGGER OVER ALLE VENNER
                               vennerUtmelding.add(v);
                            }
                        }
                }
            }

            //--------HENTER DAGENS DATO I RIKTIG FORMAT TIL SAMMENLIGNING AV DET SOM LIGGER I DB--------
            Calendar c = Calendar.getInstance();
            int aar = c.get(Calendar.YEAR);
            int mnd = c.get(Calendar.MONTH);
            int dag = c.get(Calendar.DAY_OF_MONTH);

            mnd++;
            datoIdag = dag+"/"+mnd+"/"+aar;

            //HENTER DATO FRA BESTILLINGEN
            String dato1 = b.getDato();


            //--------FORMATERER DATOENE FOR SAMMENLIGNING--------
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dato2 = null;
            Date dato4 = null;

            try {
                dato2 = sdf.parse(dato1);
                dato4 = sdf.parse(datoIdag);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //--------SAMMENLIGNINGER AV FORMATERTE DATOER--------
            //HVIS DATO ER I DAG
            if((dato2.compareTo(dato4) == 0)) {

                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


                //LEGGER INDEKS I MINNET - BENYTTES SENERE NÅR NOTIFIKASJON HENTER UT AKTUELL BESTILLING
                getSharedPreferences("APP_INFO",MODE_PRIVATE).edit().putInt("VISNINGSID", index).apply();


                //OPPRETTER INTENT
                Intent intentet = new Intent(MinService.this, SeBestillingsInfoFragment.class);
                PendingIntent pIntent = PendingIntent.getActivity(this, 0, intentet, 0); //100. FLAG  - PendingIntent.FLAG_UPDATE_CURRENT

                Notification notifikasjon = new NotificationCompat.Builder(this)
                        .setContentTitle("Påminnelse")
                        .setContentText(meldingUt)

                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent).build();
                notifikasjon.flags|= Notification.FLAG_AUTO_CANCEL;

                notificationManager.notify(m, notifikasjon);


                //-----HENTER FRA MINNE OM NOTIFIKASJON-MELDINGER ER AKTIVERT ELLER IKKE-------
                boolean mdlAvPaa = getSharedPreferences("APP_INFO",MODE_PRIVATE).getBoolean("MLDAVPAA",true);


                //-----HVIS AKTIVERT NOTIFIKASJON-----
                if(mdlAvPaa) {
                    SmsManager minSmsManager = SmsManager.getDefault();
                    //SENDER MELDING TIL ALLE DELTAKER I BESTILLINGEN
                    for(Venn v : vennerUtmelding) {
                        minSmsManager.sendTextMessage(v.getTelefon(), null, meldingUt, null, null);

                        //MELDING UT TIL BRUKER OM AT MELDING ER SENDT
                        Toast.makeText(this, "Har sendt sms", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}