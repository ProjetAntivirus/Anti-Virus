package com.example.user.antivirus.Services;

import com.example.user.antivirus.BatteryController;
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
        //algo de detection si utilisation anormale de la battery
        //ajout des données dans le contentProvider
    }
}
