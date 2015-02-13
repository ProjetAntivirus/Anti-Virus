package com.example.user.antivirus.Services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import com.example.user.antivirus.Activity.BatteryController;
import com.example.user.antivirus.ConfigApp;
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
        //Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        startService();
    }

    private void startService()
    {
        //Toast.makeText(this, "L'algorithme analyse le niveau de batterie ", Toast.LENGTH_SHORT).show();

    }


    public void onDestroy()
    {
        super.onDestroy();
        //Toast.makeText(this, "L'algorithme est stoppé", Toast.LENGTH_SHORT).show();
    }


    public void algoDetection (){
        int niveauBattery = strategieObservableBattery.getNivBattery();
        BatteryController.setText(String.valueOf(niveauBattery));
        ContentValues values = new ContentValues();
        values.put(SharedInformation.BatteryInformation.NAME, String.valueOf(niveauBattery));
        Uri uri = getContentResolver().insert(MyProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "New record inserted", Toast.LENGTH_LONG)
                .show();
        /*
        SgaProvider sga = new SgaProvider();
        String test[] = new String[2];
        // On recupere la derniere date inséré
        Cursor cursor = sga.query(SgaContract.SystemCp.CONTENT_URI, test, null, null, "ASC");
        //Si elle n'existe pas
        if (cursor == null) {
            // On insere la date actuel et le niveau de batterie actuel dans la base de donnees
            ContentValues values = new ContentValues();
            values.put(SgaContract.systemcpColumns.VALUE, niveauBattery);
            values.put(SgaContract.systemcpColumns.DATE, System.currentTimeMillis());
            Uri uri = sga.insert(SgaContract.SystemCp.CONTENT_URI, values);
        } else {
            // si la date actuel - la derniere date insere dans la base de donnees est >= 10 minutes alors
            if (cursor.getColumnIndex(SgaContract.SystemCp.DATE) - System.currentTimeMillis() <= ConfigApp.TIMER_ALGO) {
                // On insere une nouvel date dans la base de donnees avec le niveau de batterie associé
                ContentValues values = new ContentValues();
                values.put(SgaContract.systemcpColumns.VALUE, niveauBattery);
                values.put(SgaContract.systemcpColumns.DATE, System.currentTimeMillis());
                Uri uri = sga.insert(SgaContract.SystemCp.CONTENT_URI, values);
                //et on demare le service qui gère les notifications pour avertir l'utilisateur
                Intent service1 = new Intent(getApplicationContext(), NotificationService.class);
                getApplicationContext().startService(service1);
            }
        }*/
    }
}
