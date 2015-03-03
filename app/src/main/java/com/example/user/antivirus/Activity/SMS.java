package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.antivirus.R;

/**
 * Deux boutons permettant d'accéder à l'analyse des sms
 */
public class SMS extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);

        final Button sms= (Button) findViewById(R.id.buttonSMS);
        sms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMS.this, SmsController.class);
                startActivity(intent);
            }
        });
    }


}
