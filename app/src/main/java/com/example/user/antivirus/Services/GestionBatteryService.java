package com.example.user.antivirus.Services;

import com.example.user.antivirus.ConfigApp;

/**
 * Created by Max on 26/01/2015.
 */
public class GestionBatteryService {
    StrategieObservableBattery strategieObservableBattery;

    public GestionBatteryService(){
        if (ConfigApp.TEST == true){
            strategieObservableBattery = BatteryTest.getInstance();
        }
        else {
            strategieObservableBattery = new Battery();
        }
        strategieObservableBattery.add(this);
    }

    public void algoDetection (){
        int niveauBattery = strategieObservableBattery.getNivBattery();

        /*  ALGO DE DETECTION PROBLEME BATTERIE
            SgaProvider sga = new SgaProvider();
            String test[] = new String[2];
            // On récupère la derniere date de la base de donnees
            Cursor cursor = sga.query(SgaContract.SystemCp.CONTENT_URI, test, null, null, "ASC");
            // si il n'y a pas de donnees alors c'est la première fois que le code est execute
            if (cursor == null) {
            // On insere la date actuel et le niveau de batterie actuel dans la base de donnees
                ContentValues values = new ContentValues();
                values.put(SgaContract.systemcpColumns.VALUE, niveauBattery);
                values.put(SgaContract.systemcpColumns.DATE, System.currentTimeMillis());
                Uri uri = sga.insert(SgaContract.SystemCp.CONTENT_URI, values);
            } else {
                // si la date actuel - la derniere date insere dans la base de donnees est >= 10 minutes alors
                if (System.currentTimeMillis() - cursor.getColumnIndex(SgaContract.SystemCp.DATE) >= 10) {
                    // On insere une nouvel date dans la base de donnees avec le niveau de batterie associé
                    ContentValues values = new ContentValues();
                    values.put(SgaContract.systemcpColumns.VALUE, niveauBattery);
                    values.put(SgaContract.systemcpColumns.DATE, System.currentTimeMillis());
                    Uri uri = sga.insert(SgaContract.SystemCp.CONTENT_URI, values);
                    //et on demare le service qui gère les notifications
                    Intent service1 = new Intent(context, NotificationService.class);
                    context.startService(service1);
                }
            }
             }
            */

    }
}
