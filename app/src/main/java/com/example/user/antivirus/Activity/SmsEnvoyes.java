package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.user.antivirus.R;
import com.example.user.antivirus.contentProvider.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Classe analysant les sms envoyés contenu dans le content://sms/sent
public class SmsEnvoyes extends Activity {

    public TextView textSMS;
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

            HashMap<String, Object> map;

            listMess = (ListView) findViewById(R.id.listView);

            //Récupération des message contenu dans le content
            Uri allMessage = Uri.parse("content://sms/sent");
            ContentResolver cr = getContentResolver();
            Cursor c = cr.query(allMessage, null, null, null, null);
            while (c.moveToNext()) {
            /*Log.i("Num ", c.getString(2));
            Log.i("Message ", c.getString(11));
            Log.i("Date ", c.getString(4));*/

                if(isSurtax(c.getString(2))) {
                    listnum.add(c.getString(2)); // 2 -> numéro
                    listpers.add(c.getString(3)); // 3 -> personne
                    listmess.add(c.getString(11));// 11 -> body du sms
                    listdate.add(c.getString(4)); //4 -> date de l'envoi
                    ContentValues value = new ContentValues();
                    value.put(table.Sms.SMS_NUMERO, c.getString(2));
                    value.put(table.Sms.SMS_MESSAGE, c.getString(11));
                    value.put(table.Sms.SMS_DATE, c.getString(4));
                    getContentResolver().insert(table.Sms.CONTENT_SMS, value);
                }
            }
            c.close();

            // Ecrit un message sur l'interface si aucun message n'est suspect
            if(listnum.isEmpty()){
                textSMS = (TextView) findViewById(R.id.SMSVide);
                textSMS.setText("Aucun sms n'a été dernièrement envoyé à un numéro surtaxé.");
            }

            //On rempli la HashMap
            int i;
            for(i=0; i<listnum.size();i++) {
                map = new HashMap<String, Object>();
                map.put("titre", listnum.get(i));
                map.put("description", listmess.get(i));
                map.put("img", R.drawable.d_sms);
                listItem.add(map);
            }

            SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageapp,
                    new String[]{"img", "titre", "description"}, new int[]{R.id.img, R.id.titre, R.id.description});

            listMess.setAdapter(mSchedule);

        }
}
