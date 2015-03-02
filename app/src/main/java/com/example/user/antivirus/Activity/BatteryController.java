package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.antivirus.Battery;
import com.example.user.antivirus.BatteryTest;
import com.example.user.antivirus.R;
import com.example.user.antivirus.StrategieObservableBattery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class BatteryController extends Activity {
    static TextView textBatterie;
    ProgressBar progBar;
    Button btnCheckBattery;
    Intent intentBatteryUsage;

    public static void setText (String text){textBatterie.setText(text); }

    //Récupération du niveau de batterie avec le Broadcast
    BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // On récupère le niveau de la batterie
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);

            //Récupération du niveau de batterie dans le content
            //StrategieObservableBattery strat = new Battery();
            //level = strat.getNivBattery();

            //Création d'une progressBar
            progBar = (ProgressBar) findViewById(R.id.progressBar);
            // on insere le niveau de batterie dans le textBatterie
            textBatterie.setText(level + "  %");
            progBar.setMax(100);
            progBar.setProgress(level);
            progBar.getProgressDrawable().setColorFilter(Color.parseColor("#99CC00"), PorterDuff.Mode.SRC_IN);
            progBar.setContentDescription(View.TEXT_ALIGNMENT_TEXT_END + level + "%");
        }
    };

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.battery_view);
            textBatterie = (TextView) findViewById(R.id.batterie);

            IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            registerReceiver(batteryLevelReceiver, batteryLevelFilter);


            btnCheckBattery = (Button) findViewById(R.id.checkBattery);

            intentBatteryUsage = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
            ResolveInfo resolveInfo = getPackageManager().resolveActivity(intentBatteryUsage, 0);

            if (resolveInfo == null) {
                Toast.makeText(BatteryController.this, "Not Support!",
                        Toast.LENGTH_LONG).show();
                btnCheckBattery.setEnabled(false);
            } else {
                btnCheckBattery.setEnabled(true);
            }

            btnCheckBattery.setOnClickListener(new Button.OnClickListener() {

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    startActivity(intentBatteryUsage);
                }
            });

        }
 }