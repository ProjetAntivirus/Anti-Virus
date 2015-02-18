package com.example.user.antivirus.Services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import com.example.user.antivirus.Activity.BatteryController;
import com.example.user.antivirus.Battery;
import com.example.user.antivirus.BatteryTest;
import com.example.user.antivirus.ConfigApp;
import com.example.user.antivirus.StrategieObservableBattery;
import com.example.user.antivirus.contentProvider.MyProvider;
import com.example.user.antivirus.contentProvider.SharedInformation;

/**
 * Created by Max on 26/01/2015.
 */
public class GestionBatteryService extends Service{
    StrategieObservableBattery strategieObservableBattery;
    private Context ctx;

    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public void onCreate()
    {
        super.onCreate();
        ctx = this;
        if (ConfigApp.TEST == true){
            strategieObservableBattery = new BatteryTest();
        }
        else {
            strategieObservableBattery = new Battery();
        }
        strategieObservableBattery.add(this);
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
        startService();
    }

    private void startService()
    {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();

    }


    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service stoped", Toast.LENGTH_SHORT).show();
    }


    private void insertData(int niveauBattery){
        ContentValues values = new ContentValues();
        values.put(SharedInformation.BatteryInformation.LEVEL, niveauBattery);
        values.put(SharedInformation.BatteryInformation.DATE, System.currentTimeMillis());
        getContentResolver().insert(MyProvider.CONTENT_URI, values);
        Toast.makeText(this, "Insertion de : "+String.valueOf(niveauBattery)+System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
    }

    public void algoDetection (){
        int niveauBattery = strategieObservableBattery.getNivBattery();
        BatteryController.setText(String.valueOf(niveauBattery));
        MyProvider provider = new MyProvider();
        String columns[] = new String[] {SharedInformation.BatteryInformation.ID, SharedInformation.BatteryInformation.LEVEL, SharedInformation.BatteryInformation.DATE };
        Uri mContacts = MyProvider.CONTENT_URI;
        Cursor cursor = provider.query(mContacts, columns, null, null, "ASC");
        //Si la base de données est vide
        if (cursor == null) {
            insertData(niveauBattery);
        } else {
            // si la date actuel - la derniere date insere dans la base de donnees est <= TIME_ALGO minutes alors
            if (cursor.getColumnIndex(SharedInformation.BatteryInformation.DATE) - System.currentTimeMillis() <= ConfigApp.TIMER_ALGO) {
                insertData(niveauBattery);
                //et on demare le service qui gère les notifications pour avertir l'utilisateur
                Intent service1 = new Intent(getApplicationContext(), NotificationService.class);
                getApplicationContext().startService(service1);
            }
        }
    }
}
