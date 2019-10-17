package com.skole.s304114mappe2ny;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.skole.s304114mappe2ny.Fragmenter.NotifikasjonFragment;

public class MinBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

        Toast.makeText(context, "I BroadcastReceiver", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(context, SettPeriodiskService.class);
        //Erstatter gammel service hvis den kjøres igjen
        i.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
        context.startService(i);




        /*Intent i = new Intent(context, MinService.class);
        context.startService(i);*/

        //TIL SettPeriodiskService

        //}
    }
}