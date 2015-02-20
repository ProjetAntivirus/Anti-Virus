package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
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

import com.example.user.antivirus.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class BatteryController extends Activity {
    static TextView textBatterie;
    private ListView listApp;
    ProgressBar progBar;
    Button btnCheckBattery;
    Intent intentBatteryUsage;

    public static void setText (String text){
        textBatterie.setText(text);
    }

        public List<ApplicationInfo> applicationName() {
            //Récupère la liste des applications installées
            PackageManager appInfo = getPackageManager();
            List<ApplicationInfo> list = appInfo.getInstalledApplications(0);
            //Trie les applications par leur nom d'affichage
            Collections.sort(list, new ApplicationInfo.DisplayNameComparator(appInfo));
            for (ApplicationInfo applicationInfo : list) {
                //Récupère le nom de l'application
                Log.i("ApplicationList", "application=" + getPackageManager().getApplicationLabel(applicationInfo));
            }
            return list;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.battery_view);
            textBatterie = (TextView) findViewById(R.id.batterie);
            /*
            IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            registerReceiver(new BroadcastReceiverBattery(), batteryLevelFilter);
            progBar = (ProgressBar) findViewById(R.id.progressBar);


*/
            //StrategieObservableBattery strat = new BatteryTest();
           // strat.setNivBattery(30);
            //Récupération de la listview créée dans le fichier Listapp.xml


            btnCheckBattery = (Button)findViewById(R.id.checkBattery);

            intentBatteryUsage = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
            ResolveInfo resolveInfo = getPackageManager().resolveActivity(intentBatteryUsage,0);

            if(resolveInfo == null){
                Toast.makeText(BatteryController.this, "Not Support!",
                        Toast.LENGTH_LONG).show();
                btnCheckBattery.setEnabled(false);
            }else{
                btnCheckBattery.setEnabled(true);
            }

            btnCheckBattery.setOnClickListener(new Button.OnClickListener(){

                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    startActivity(intentBatteryUsage);
                }});


            listApp = (ListView) findViewById(R.id.listapp);

            //récupérer applications (nom et icon)
            List<ApplicationInfo> listAppli = applicationName();
            List<String> listNameApp = new ArrayList<String>();
            List<Drawable> listIconApp = new ArrayList();

            for (ApplicationInfo applicationInfo : listAppli) {
                PackageManager pack = getPackageManager();
                listNameApp.add((String) pack.getApplicationLabel(applicationInfo));
                listIconApp.add(pack.getApplicationIcon(applicationInfo));
            }

            //Création de la ArrayList qui nous permettra de remplire la listView
            ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

            //On déclare la HashMap qui contiendra les informations pour un item
            HashMap<String, Object> map;

            Drawable image;
            int i = 0;
            for (String app : listNameApp) {
                //Drawable image = getResources().getDrawable(i);
                image = null;
                app = null;
                app = listNameApp.get(i);
                image = listIconApp.get(i);


                //Création d'une HashMap pour insérer les informations du premier item de notre listView
                map = new HashMap<String, Object>();
                //on insère le nom de l'application
                map.put("titre", app);
                //on insère le pourcentage de batterie utilisée
                map.put("description", "Batterie utilisée :");
                //on insère la référence à l'image  que l'on récupérera dans l'imageView créé dans le fichier.xml
                map.put("img", image);
                //enfin on ajoute cette hashMap dans la arrayList
                listItem.add(map);

                ++i;
            }

            //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
            SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageapp,
                    new String[]{"img", "titre", "description"}, new int[]{R.id.img, R.id.titre, R.id.description});

            //On attribut à notre listView l'adapter que l'on vient de créer
            listApp.setAdapter(mSchedule);

            mSchedule.setViewBinder(new SimpleAdapter.ViewBinder() {
                public boolean setViewValue(View view, Object data, String textRepresentation) {
                    if (view instanceof ImageView && data instanceof Drawable) {
                        ImageView iv = (ImageView) view;
                        iv.setImageDrawable((Drawable) data);
                        return true;
                    } else return false;
                }
            });
        }
 }