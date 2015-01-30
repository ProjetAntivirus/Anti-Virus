package com.example.user.antivirus;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class BatteryController extends Activity {
    static TextView textBatterie;
    private ListView listApp;
   // public GestionBatteryService gestion = new GestionBatteryService();
    ProgressBar progBar;

    public static void setText (String text){
        textBatterie.setText(text);
    }
    /*BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // On récupère le niveau de la batterie
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);

            // on insere le niveau de batterie dans le textBatterie de l'affichage pour un niveau en temps reel
            textBatterie.setText("\t" + level + "  % \n");
            progBar.setMax(100);
            progBar.setProgress(level);
            progBar.getProgressDrawable().setColorFilter(Color.parseColor("#99CC00"), PorterDuff.Mode.SRC_IN);
            progBar.setContentDescription(View.TEXT_ALIGNMENT_TEXT_END+level+"%");




            // TEST
            // Toast de test pour vérifier que le broadcast intervient bien au changement de niveau
            // Toast.makeText(context, "Battery niveau/Action Changed" + level, Toast.LENGTH_LONG).show();

/*  TEST CONTENT PROVIDER : INSERT ET QUERY*/
            //SgaProvider sga = new SgaProvider();
            //sga.onCreate();
            // sga.query(SgaContract.BASE_CONTENT_URI,);
            //ContentValues values = new ContentValues();
            //values.put( SgaContract.systemcpColumns.VALUE , "test");
                //Uri uri = sga.insert(SgaContract.SystemCp.CONTENT_URI, values);
            //textBatterie.setText("N " + "erreur insert" + "\n");/*
        /*if (uri == null)
            textBatterie.setText("N " + "bonjour" +"\n");
        else {
            String test[] = new String[2];
            test[0] = SgaContract.systemcpColumns.VALUE;
            Cursor cursor = sga.query(uri, test, null, null, "ASC");
            textBatterie.setText("N " + cursor.toString() + "\n");
        }
*/

/*  ALGO DE DETECTION PROBLEME BATTERIE
            SgaProvider sga = new SgaProvider();
            String test[] = new String[2];
            // On récupère la derniere date de la base de donnees
            Cursor cursor = sga.query(SgaContract.SystemCp.CONTENT_URI, test, null, null, "ASC");
            // si il n'y a pas de donnees alors c'est la première fois que le code est execute
            if (cursor == null) {
            // On insere la date actuel et le niveau de batterie actuel dans la base de donnees
                ContentValues values = new ContentValues();
                values.put(SgaContract.systemcpColumns.VALUE, level);
                values.put(SgaContract.systemcpColumns.DATE, System.currentTimeMillis());
                Uri uri = sga.insert(SgaContract.SystemCp.CONTENT_URI, values);
            } else {
                // si la date actuel - la derniere date insere dans la base de donnees est >= 10 minutes alors
                if (System.currentTimeMillis() - cursor.getColumnIndex(SgaContract.SystemCp.DATE) >= 10) {
                    // On insere une nouvel date dans la base de donnees avec le niveau de batterie associé
                    ContentValues values = new ContentValues();
                    values.put(SgaContract.systemcpColumns.VALUE, level);
                    values.put(SgaContract.systemcpColumns.DATE, System.currentTimeMillis());
                    Uri uri = sga.insert(SgaContract.SystemCp.CONTENT_URI, values);
                    //et on demare le service qui gère les notifications
                    Intent service1 = new Intent(context, NotificationSevice.class);
                    context.startService(service1);
                }
            }

        }
        };*/


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
            gestion.algoDetection();
            IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            registerReceiver(new BroadcastReceiverBattery(), batteryLevelFilter);
            progBar = (ProgressBar) findViewById(R.id.progressBar);
*/

            //Récupération de la listview créée dans le fichier Listapp.xml
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