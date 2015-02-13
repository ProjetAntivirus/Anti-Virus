package com.example.user.antivirus.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.user.antivirus.Services.GestionBatteryService;

/**
 * Created by Pierre on 03/01/2015.
 */
public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Intent launch = new Intent(context, GestionBatteryService.class);
        context.startService(launch);
    }
}
