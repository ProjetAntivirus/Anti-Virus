package com.example.user.antivirus.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.user.antivirus.Activity.ContactRepertory;

public class SMSReceiver extends BroadcastReceiver
{
    private final String	ACTION_RECEIVE_SMS	= "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // La réception d'un sms est détecté
        if (intent.getAction().equals(ACTION_RECEIVE_SMS))
        {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                /*
                *Recupération des données du sms.
                 */
                Object[] pdus = (Object[]) bundle.get("pdus");

                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++)  {  messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);  }  if (messages.length > -1)
            {
                final String messageBody = messages[0].getMessageBody();
                final String phoneNumber = messages[0].getDisplayOriginatingAddress();

                /*
                * On regarde si le numéro est un numéro surtaxé. Si c'est le cas on enregistre le numéro dans un content provider.
                * et on avertis l'utilisateur.
                 */
                if(ContactRepertory.isSurtaxed(phoneNumber)){
                    ContactRepertory.insertNum(phoneNumber);
                    Toast.makeText(context, phoneNumber + " Message Dangereux", Toast.LENGTH_LONG).show();
                }
            }
            }
        }


    }

}
