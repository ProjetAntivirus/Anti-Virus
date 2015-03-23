package com.example.user.antivirus.Activity;

/**
 * Affichage dans une ExpandableListView les différentes applications installées
 * ainsi que les services utilisés pour chacunes d'elles.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.user.antivirus.R;

public class AppServices extends Activity {

    ExpandablelistAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<Drawable> listImage;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_service_view);

        expListView = (ExpandableListView) findViewById(R.id.ExpListView);

        prepareListData();

        listAdapter = new ExpandablelistAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

    }

    /*
     * Prepare la liste des applications et de leurs permissions
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        listImage = new ArrayList<>();

        List<String> listNameApp = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        int j = 0;
        for (ApplicationInfo applicationInfo : packages) {
            //Log.d("test", "App: " + applicationInfo.name + " Package: " + applicationInfo.packageName);

            listNameApp.add((String) pm.getApplicationLabel(applicationInfo));
            //Ajout de l'application
            listDataHeader.add(listNameApp.get(j));
            List<String> listService = new ArrayList<>();

            try {

                //Récupérer la liste des services utilisé pour chaque application
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_SERVICES);
                ServiceInfo[] requestedService = packageInfo.services;
                String Service = " * ";
                listService.clear();

                if (requestedService == null) {
                    Service = "Aucun service n'est utilisé pour cette application";
                    listService.add(Service);
                } else {
                    for (int i = 0; i < requestedService.length; i++) {
                        // Log.d("test", requestedPermissions[i]);
                        Pattern p = Pattern.compile("[A-Z]");

                        String entree = requestedService[i].name;
                        Matcher m = p.matcher(entree);
                        String name = " * ";
                        int position = 0;
                        if (m.find()) {
                            position = m.start();
                        }
                        //Récupère juste le nom (en majuscule) de la permission
                        name = entree.substring(position, entree.length());


                        listService.add(name);
                    }
                }

                //Ajout des permissions
                listDataChild.put(listDataHeader.get(j), listService);

                //SI l'ajout ne marche pas on change le nom du Header
                if(listDataChild.size() != listDataHeader.size())
                {
                    listDataHeader.set(j, listDataHeader.get(j)+"  ");
                    listDataChild.put(listDataHeader.get(j), listService);
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                // Meme si l'une des permissions génère cette excepetion on ajoute la liste
                listDataChild.put(listDataHeader.get(j), listService);
            }
            j++;
        }

    }
}