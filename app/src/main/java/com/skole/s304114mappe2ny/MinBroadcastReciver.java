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
        Toast.makeText(context, "I BroadcastReceiver", Toast.LENGTH_SHORT).show();

        /*Intent i = new Intent(context, MinService.class);
        context.startService(i);*/

        //TIL SettPeriodiskService
        Intent i = new Intent(context, SettPeriodiskService.class);
        context.startService(i);

    }
}