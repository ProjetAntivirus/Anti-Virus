package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.antivirus.R;

import java.io.File;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/*
    Classe s'occupant du traitement de la mémoire
 */
public class MemoryController extends Activity {

    TextView textMemoire;
    private ListView listApplication;

    // récupération des applications installées sur le mobile
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


    //Notification memoire
    public int ID_NOTIFICATION = 0;
    int icon = R.drawable.and_virus;
    CharSequence tickerText = "Notification Mémoire";


    ActivityManager activityManager;
    ActivityManager.MemoryInfo memoryInfo;
    final String TAG = "MemInfo";

    public void getFreeMemory() {
            /*Parcelable p = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager.MemoryInfo.
                    activityManager.getMemoryInfo(mi);
            long freeMemory = mi.availMem / 1048576L;
            long memory = mi.describeContents();
            textMemoire.setText(memory + " / " + freeMemory);*/

            /*activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            memoryInfo = new ActivityManager.MemoryInfo();

            activityManager.getMemoryInfo(memoryInfo);

            Log.i(TAG, " memoryInfo.availMem " + memoryInfo.availMem);
            Log.i(TAG, " memoryInfo.lowMemory " + memoryInfo.lowMemory);
            Log.i(TAG, " memoryInfo.threshold " + memoryInfo.threshold);

            Toast.makeText(getApplicationContext(), String.valueOf(memoryInfo.availMem), Toast.LENGTH_LONG)
                    .show();*/
    }



    public long TotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Total  = ( (long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        return Total;
    }

    public long FreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Free   = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        return Free;
    }

    public long BusyMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Total  = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        long   Free   = (statFs.getAvailableBlocks()   * (long) statFs.getBlockSize());
        long   Busy   = Total - Free;
        return Busy;
    }


    public static String floatForm (double d)
    {
        return new DecimalFormat("#.##").format(d);
    }


    public static String convertion (long size)
    {
        //size = size /8;
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size <  Kb)                 return floatForm(        size     ) + " byte";
        if (size >= Kb && size < Mb)    return floatForm((double)size / Kb) + " Kb";
        if (size >= Mb && size < Gb)    return floatForm((double)size / Mb) + " Mb";
        if (size >= Gb && size < Tb)    return floatForm((double)size / Gb) + " Gb";
        if (size >= Tb && size < Pb)    return floatForm((double)size / Tb) + " Tb";
        if (size >= Pb && size < Eb)    return floatForm((double)size / Pb) + " Pb";
        if (size >= Eb)                 return floatForm((double)size / Eb) + " Eb";

        return "???";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_view);

        textMemoire = (TextView) findViewById(R.id.memory);
        long totalMemory = TotalMemory();
        String memTotal = convertion(totalMemory); // 1,15 Gb
        long freeMemory = FreeMemory();
        String memDispo = convertion(freeMemory); // 298,51 Mb
        long busyMemory = BusyMemory();
        String memOccup = convertion(busyMemory); // 882,62 Mb
        textMemoire.setText("\nMémoire totale : "+memTotal+"\n"
                + "Mémoire disponible : "+memDispo+"\n"
                + "Mémoire occupée : "+memOccup+"\n");



        //Récupération de la listview créée dans le fichier Listapp.xml
        listApplication = (ListView) findViewById(R.id.listapp);

        //récupérer applications (nom et icon)
        List<ApplicationInfo> listAppli= applicationName();
        final List<String> listNameApp = new ArrayList<String>();
        List<Drawable> listIconApp = new ArrayList();
        // List<int> listMemoryApp = new ArrayList();

        for(ApplicationInfo applicationInfo : listAppli){
            PackageManager pack = getPackageManager();
            listNameApp.add((String)pack.getApplicationLabel(applicationInfo));
            listIconApp.add(pack.getApplicationIcon(applicationInfo));
            // listMemoryApp.add(String.valueOf(pack.ge));
        }


        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, Object> map;

        int memory;
        Drawable image;
        int i = 0;
        for(String app : listNameApp) {
            image = null;
            app = null;
            memory = 0;
            app = listNameApp.get(i);
            image =listIconApp.get(i);


            //Création d'une HashMap pour insérer les informations du premier item de notre listView
            map = new HashMap<String, Object>();
            //on insère le nom de l'application
            map.put("titre", app);
            //on insère le pourcentage de batterie utilisée
            map.put("description", memory);
            //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
            map.put("img", image);
            //enfin on ajoute cette hashMap dans la arrayList
            listItem.add(map);

            ++i;
        }

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichageapp,
                new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});

        //On attribut à notre listView l'adapter que l'on vient de créer
        listApplication.setAdapter(mSchedule);
        mSchedule.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Drawable) {
                    ImageView iv = (ImageView) view;
                    iv.setImageDrawable((Drawable) data);
                    return true;
                }
                else return false;
            }
        });

        listApplication.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on récupère la HashMap contenant les infos de notre app
                HashMap<String, String> map = (HashMap<String, String>) listApplication.getItemAtPosition(position);
                //on créer une boite de dialogue
                AlertDialog.Builder adb = new AlertDialog.Builder(MemoryController.this);
                //on attribut un titre à notre boite de dialogue
                adb.setMessage(map.get("titre"));
                final String name = map.get("titre");
                //on insère un message à notre boite de dialogue, et ici on affiche le titre de l'item cliqué
                adb.setMessage("Voulez-vous désinstaller cette application ?");
                //on indique que l'on veut des boutons
                adb.setNegativeButton("Annuler", null);
                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){

                        List<ApplicationInfo> listAppli= applicationName();
                        for(ApplicationInfo applicationInfo : listAppli){
                            PackageManager pack = getPackageManager();
                            String app =(String)pack.getApplicationLabel(applicationInfo);
                            if(app == name){
                                String dir = applicationInfo.sourceDir;
                                String name = applicationInfo.packageName;
                                //String path = applicationInfo.installLocation;
                                Uri packageUri = Uri.parse(name);
                                Intent uninstallIntent =
                                        new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri );
                                startActivity(uninstallIntent);
                            }
                        }

                        Uri packageUri = Uri.parse("package:com.packageName");
                        Intent uninstallIntent =
                                new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
                        startActivity(uninstallIntent);
                    }
                });
                //on affiche la boite de dialogue
                adb.show();
            }
        });

    }
}

