package com.example.user.antivirus.Services;

import java.util.ArrayList;

/**
 * Created by Max on 26/01/2015.
 */
public abstract class StrategieObservableBattery {
    protected static int nivBattery;

    private ArrayList<GestionBatteryService> observateurs = new ArrayList<>();

    public void add (GestionBatteryService gestionBattery){
        observateurs.add(gestionBattery);
    }

    public void del (GestionBatteryService gestionBattery){

    }

    private void notifier(){
        for (GestionBatteryService battery : observateurs){
            battery.algoDetection();
        }
    }

    public int getNivBattery (){
        return nivBattery;
    }

    public void setNivBattery (int nivBatery){
        this.nivBattery = nivBatery;
        this.notifier();
    }
}
