package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.antivirus.R;

/**
 * Deux boutons permettant l'analyse du repertoire et des sms envoyés
 */
public class SmsController extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_view);


        final Button view = (Button) findViewById(R.id.repertoryButton);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmsController.this, ContactRepertory.class);
                startActivity(intent);
            }
        });

        final Button view2 = (Button) findViewById(R.id.smsEnvoye);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmsController.this, SmsEnvoyes.class);
                startActivity(intent);
            }
        });

        }

}

