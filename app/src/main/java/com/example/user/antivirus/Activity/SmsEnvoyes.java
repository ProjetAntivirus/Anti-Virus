package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.user.antivirus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SmsEnvoyes extends Activity {

    private ListView listMess;

    //Vérification si le numéro est surtaxé
    private boolean isSurtax(String phone){
        if (phone.startsWith("3") || phone.startsWith("4") ||  phone.startsWith("5") || phone.startsWith("6") || phone.startsWith("7") || phone.startsWith("+8")){
            return true;
        }
        return false;
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.analyse_sms_envoye);

            List<String> listmess = new ArrayList<>();
            List<String> listnum = new ArrayList<>();
            List<String> listdate = new ArrayList<>();
            List<String> listpers = new ArrayList<>();

            //Création de la ArrayList qui nous permettra de remplire la listView
            ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

            //On déclare la HashMap qui contiendra les informations pour un item
            HashMap<String, Object> map;


            listMess = (ListView) findViewById(R.id.listView);

            Uri allMessage = Uri.parse("content://sms/sent");
            ContentResolver cr = getContentResolver();
            Cursor c = cr.query(allMessage, null, null, null, null);
            while (c.moveToNext()) {
            /*Log.i("Num ", c.getString(2));
            Log.i("Message ", c.getString(11));
            Log.i("Date ", c.getString(4));*/

                if(isSurtax(c.getString(2))) {
                    listnum.add(c.getString(2));
                    listpers.add(c.getString(3));
                    listmess.add(c.getString(11));
                    listdate.add(c.getString(4));
                }
            }
            c.close();

            int i;
            for(i=0; i<listnum.size();i++) {

                //Création d'une HashMap pour insérer les informations du premier item de notre listView
                map = new HashMap<String, Object>();

                //on insère le nom de l'application
                map.put("titre", listnum.get(i));
                //on insère le pourcentage de batterie utilisée
                map.put("description", listmess.get(i));
                //on insère la référence à l'image  que l'on récupérera dans l'imageView créé dans le fichier.xml
                map.put("img", R.drawable.d_sms);
                //enfin on ajoute cette hashMap dans la arrayList
                listItem.add(map);
            }


            //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
            SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageapp,
                    new String[]{"img", "titre", "description"}, new int[]{R.id.img, R.id.titre, R.id.description});

            //On attribut à notre listView l'adapter que l'on vient de créer
            listMess.setAdapter(mSchedule);

        }
}
