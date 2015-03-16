package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.antivirus.R;


/**
 * Created by user on 07/03/2015.
 */
public class Applications extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application);

        final Button Permission = (Button) findViewById(R.id.permissions);
        Permission.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Applications.this, Permissions.class);
                startActivity(intent);
            }
        });

        final Button Signature = (Button) findViewById(R.id.signature);
        Signature.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Applications.this, EnConstruction.class);
                startActivity(intent);
            }
        });

        final Button Service = (Button) findViewById(R.id.service);
        Service.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Applications.this, AppServices.class);
                startActivity(intent);
            }
        });

    }
}
