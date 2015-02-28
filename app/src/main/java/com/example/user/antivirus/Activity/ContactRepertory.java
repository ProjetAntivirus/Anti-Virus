package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.antivirus.R;

import java.util.ArrayList;

/**
 * Created by Pierre on 08/02/2015.
 */
public class ContactRepertory extends Activity {

    private ArrayList values = new ArrayList<String>();
    ListView listView;

    /*
    * Création de l'activité qui affichera la liste des numeros surtaxés
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyse_repertoire);
        listView = (ListView) findViewById(R.id.list);
        viewContact();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), itemValue, Toast.LENGTH_LONG).show();
                deleteContact(itemValue);
                Intent intent = new Intent(ContactRepertory.this, ContactRepertory.class);
                startActivity(intent);
            }
        });
    }

    /*
    *   Metgode qui permet d'analyser et d'afficher les numero surtaxés
     */
    private void viewContact(){
    ContentResolver cr = getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null);
    if (cur.getCount() > 0) {
        while (cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            if (Integer.parseInt(cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (pCur.moveToNext()) {
                    String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if(isSurtaxed(phoneNo)){
                        values.add(name + " : " + phoneNo);
                        insertNum(phoneNo);
                    }
                }
                pCur.close();
            }
        }
    }
}

    /*
    *   Methode static qui permet de savoir si un numéro est surtxé ou non
    *   Renvoie true si le numéro est surtxé. Renvoie False sinon.
    */
    public static boolean isSurtaxed(String phone){
        if (phone.startsWith("08") || phone.startsWith("0 8") || phone.startsWith("3") || phone.startsWith("+338") || phone.startsWith("+331")){
            return true;
        }
        return false;
    }

    /*
    *   Méthode static qui permet l'insertion d'un numéro surtaxé dans le content provider
    *   Utilisé par Contactrepertory, SMSReceiver et SMSEnvoye
    */
    public static void insertNum(String phoneNumber){
        /*
        ContentValues values = new ContentValues();
        values.put(SharedInformation.BatteryInformation.NUM, phoneNumber);
        values.put(SharedInformation.BatteryInformation.DATE, System.currentTimeMillis());
        getContentResolver().insert(MyProvider.CONTENT_URI, values);
        */
    }


    /*
    *   Méthode qui permet de supprimer un contact du repertoire
     */
    private void deleteContact(String s) {
        String f [] = s.split (" : ") ;
        String name = f[0];
        ContentResolver cr = getContentResolver();
        String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
        String[] params = new String[]{name};
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(where, params)
                .build());
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }
}
