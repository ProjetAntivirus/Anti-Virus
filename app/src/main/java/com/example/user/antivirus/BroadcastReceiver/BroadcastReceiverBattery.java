package com.example.user.antivirus.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.example.user.antivirus.Services.Battery;
import com.example.user.antivirus.Services.StrategieObservableBattery;

/**
 * Created by Max on 26/01/2015.
 */
public class BroadcastReceiverBattery extends BroadcastReceiver {
    StrategieObservableBattery battery = new Battery();

    @Override
    public void onReceive (Context context, Intent intent){
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        battery.setNivBattery(level);
    }
}
