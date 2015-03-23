package com.example.user.antivirus.Services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.widget.Toast;

import com.example.user.antivirus.Battery;
import com.example.user.antivirus.BatteryTest;
import com.example.user.antivirus.ConfigApp;
import com.example.user.antivirus.StrategieObservableBattery;
import com.example.user.antivirus.contentProvider.table;

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

    /*
    Création du service en fonction de la methode de détection utilisée.
     */
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

    /*
    * On démarre le servive
     */
    private void startService()
    {
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();

    }

    /*
    * On stop le service
     */
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service stoped", Toast.LENGTH_SHORT).show();
    }


    /*
    * Methode d'insertion du niveau de batterie à une date donnée du systeme.
     */
    private void insertData(int niveauBattery){
        ContentValues values = new ContentValues();
        values.put(table.Battery.BATTERY_LEVEL, niveauBattery);
        values.put(table.Battery.BATTERY_DATE, System.currentTimeMillis());
        getContentResolver().insert(table.Battery.CONTENT_BATTERY, values);
    }

    /*
    * Algrithme de détection d'un problème sur le niveau de la batterie.
    * Recupère la derniere date à laquelle il y a eu une modification du niveau de batterie, la campare à la date actuelle
    * Si le temps est trop court, alors on leve une notiication
    * On insere la dernier date de changement de batterie dans le content provider
     */
    public void algoDetection (){
        int niveauBattery = strategieObservableBattery.getNivBattery();
        Cursor cursor = getContentResolver().query(table.Battery.CONTENT_BATTERY, null, null, null, null);
        //Si la base de données est vide
        if (cursor.getCount() == 0) {
            insertData(niveauBattery);
        } else {
            // si la date actuel - la derniere date insere dans la base de donnees est <= TIME_ALGO minutes alors
            if (cursor.getLong(cursor.getColumnIndex(table.Battery.BATTERY_DATE)) - System.currentTimeMillis() <= ConfigApp.TIMER_ALGO) {
                insertData(niveauBattery);
                //et on demare le service qui gère les notifications pour avertir l'utilisateur
                Intent service1 = new Intent(getApplicationContext(), NotificationService.class);
                getApplicationContext().startService(service1);
            }
        }
    }
}
