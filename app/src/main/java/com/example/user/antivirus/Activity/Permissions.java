package com.example.user.antivirus.Activity;

/**
 * Affichage dans une ExpandableListView les différentes applications installées
 * ainsi que les permission requises pour chacunes d'elles.
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
import android.content.pm.PermissionInfo;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.user.antivirus.R;

public class Permissions extends Activity {

    ExpandablelistAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<Drawable> listImage;
    HashMap<String, List<String>> listDataChild;
    //ImageView Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

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
        //Image = (ImageView)  findViewById(R.id.img);


        List<String> listNameApp = new ArrayList<>();
        List<String> listpermission = new ArrayList<>();
        List<Drawable> listIconApp = new ArrayList();


        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        int j = 0;
        for (ApplicationInfo applicationInfo : packages) {
            //Log.d("test", "App: " + applicationInfo.name + " Package: " + applicationInfo.packageName);

            listNameApp.add((String) pm.getApplicationLabel(applicationInfo));
            //listIconApp.add(pm.getApplicationIcon(applicationInfo));
            //Ajout de l'application
            listDataHeader.add(listNameApp.get(j));
            //listImage.add(listIconApp.get(j));

            try {

                //Récupérer la liste des permissions
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                String[] requestedPermissions = packageInfo.requestedPermissions;
                String Permission = " * ";
                listpermission.clear();

                if (requestedPermissions == null)
                {
                    Permission = "Aucune permission n'est requise pour cette application";
                    listpermission.add(Permission);
                }
                else {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        // Log.d("test", requestedPermissions[i]);
                        Pattern p = Pattern.compile("[A-Z]");

                        String entree = requestedPermissions[i];
                        Matcher m = p.matcher(entree);
                        String name = " * ";
                        int position = 0;
                        if (m.find()) {
                            position = m.start();
                        }
                        //Récupère juste le nom (en majuscule) de la permission
                        name = entree.substring(position, entree.length());
                        //Récupère la description de la permission
                        PermissionInfo PI = pm.getPermissionInfo(requestedPermissions[i], pm.GET_META_DATA);
                        CharSequence CS = PI.loadDescription(pm);
                        if (CS == null) {
                            CS = "Aucune desciption.";
                        }
                        Permission = name + " : " + CS.toString();

                        listpermission.add(Permission);
                    }
                }

                //Ajout des permissions
                listDataChild.put(listDataHeader.get(j), listpermission);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                // Meme si l'une des permissions génère cette exception on ajoute quand même les autres dans la liste
                listDataChild.put(listDataHeader.get(j), listpermission);
            }
            j++;
        }

    }
}
