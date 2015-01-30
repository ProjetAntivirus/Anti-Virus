package com.example.user.antivirus.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

/**
 * Created by Max on 26/01/2015.
 */
public class BroadcastReceiverBattery extends BroadcastReceiver {
    StrategieObservableBattery battery = new Battery ();

    @Override
    public void onReceive (Context context, Intent intent){
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        battery.setNivBattery(level);
    }
}
