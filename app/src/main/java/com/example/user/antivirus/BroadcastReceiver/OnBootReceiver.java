package com.example.user.antivirus.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Pierre on 03/01/2015.
 */
public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){


        Toast.makeText(context, "OnBootBroadcast démar le service. ", Toast.LENGTH_LONG).show();
    }
}
