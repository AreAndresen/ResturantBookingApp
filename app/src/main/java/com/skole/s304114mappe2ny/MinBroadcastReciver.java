package com.skole.s304114mappe2ny;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MinBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //INTENT
        Intent i = new Intent(context, SettPeriodiskService.class);

        i.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));

        //starter service
        context.startService(i);
    }
}