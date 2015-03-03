package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.user.antivirus.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/*
    Classe s'occupant du traitement (de l'analyse) de la mémoire (smartphone et applications)
 */
public class MemoryController extends Activity {

    TextView textMemoire;
    private ListView listApplication;

    // récupération des applications installées sur le mobile
    public List<ApplicationInfo> applicationName() {
        PackageManager appInfo = getPackageManager();
        List<ApplicationInfo> list = appInfo.getInstalledApplications(0);
        //Trie les applications par leur nom d'affichage
        Collections.sort(list, new ApplicationInfo.DisplayNameComparator(appInfo));
        for (ApplicationInfo applicationInfo : list) {
            Log.i("ApplicationList", "application=" + getPackageManager().getApplicationLabel(applicationInfo));
        }
        return list;
    }

    //Mémoire totale du smartphone
    public long TotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Total  = ( (long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        return Total;
    }

    //Mémoire disponible du smartphone
    public long FreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Free   = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        return Free;
    }

    //Mémoire occupée du smartphone
    public long BusyMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Total  = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        long   Free   = (statFs.getAvailableBlocks()   * (long) statFs.getBlockSize());
        long   Busy   = Total - Free;
        return Busy;
    }

    //Convertion pour la taille
    private String Size(long size){
        return Formatter.formatFileSize(MemoryController.this, size);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_view);

        textMemoire = (TextView) findViewById(R.id.memory);
        long totalMemory = TotalMemory();
        String memTotal = Size(totalMemory);
        long freeMemory = FreeMemory();
        String memDispo = Size(freeMemory);
        long busyMemory = BusyMemory();
        String memOccup = Size(busyMemory);
        //insertion des valaurs correspondant à la mémoire du smartphone
        textMemoire.setText("\nMémoire totale : "+memTotal+"\n"
                + "Mémoire disponible : "+memDispo+"\n"
                + "Mémoire occupée : "+memOccup+"\n");

        listApplication = (ListView) findViewById(R.id.listapp);

        //récupérer applications (nom, icon, taille)
        List<ApplicationInfo> listAppli= applicationName();
        final List<String> listNameApp = new ArrayList<String>();
        final List<Drawable> listIconApp = new ArrayList();
        ArrayList listMemoryApp = new ArrayList();

        for(ApplicationInfo applicationInfo : listAppli){
            PackageManager pack = getPackageManager();
            listNameApp.add((String)pack.getApplicationLabel(applicationInfo));
            listIconApp.add(pack.getApplicationIcon(applicationInfo));

            long size = new File(applicationInfo.sourceDir).length();
            listMemoryApp.add(Size(size));
        }


        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, Object> map;

        Object memory;
        Drawable image;
        int i = 0;
        for(String app : listNameApp) {
            image = null;
            app = null;
            memory = listMemoryApp.get(i);
            app = listNameApp.get(i);
            image =listIconApp.get(i);


            //Création d'une HashMap et insertion des différents éléments
            map = new HashMap<String, Object>();
            map.put("titre", app);
            map.put("description", memory);
            map.put("img", image);
            //ajout hashMap dans arrayList
            listItem.add(map);

            ++i;
        }

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichageapp,
                new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});

        //On attribut à notre listView l'adapter
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
                //HashMap contenant les infos de notre app
                HashMap<String, String> map = (HashMap<String, String>) listApplication.getItemAtPosition(position);
                //on créer une boite de dialogue
                AlertDialog.Builder adb = new AlertDialog.Builder(MemoryController.this, R.style.AlertDialogCustom);
                //remplissage de la boite de dialogue
                adb.setIcon(listIconApp.get(position));
                adb.setTitle(map.get("titre"));
                final String name = map.get("titre");

                adb.setMessage("Voulez-vous désinstaller cette application ?");

                adb.setNegativeButton("Annuler", null);
                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){

                        //récupération du nom du package pour la désinstallation de l'appli
                        List<ApplicationInfo> listAppli= applicationName();
                        for(ApplicationInfo applicationInfo : listAppli){
                            PackageManager pack = getPackageManager();
                            String app =(String)pack.getApplicationLabel(applicationInfo);
                            if(app == name){
                                String name = applicationInfo.packageName;
                                Uri packageURI = Uri.parse("package:"+name);
                                startActivity(new Intent(Intent.ACTION_DELETE, packageURI));
                            }
                        }
                    }
                });
                //on affiche la boite de dialogue
                Dialog d = adb.show();
                int dividerId = d.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
                View divider = d.findViewById(dividerId);
                divider.setBackgroundColor(getResources().getColor(R.color.android));

            }
        });

    }
}

