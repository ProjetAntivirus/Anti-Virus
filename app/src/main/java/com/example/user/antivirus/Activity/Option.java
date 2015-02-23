package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.antivirus.R;

/**
 * Created by user on 04/12/2014.
 */
public class Option extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);


        final Button batterie = (Button) findViewById(R.id.buttonBatterie);
        batterie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option.this, BatteryController.class);
                startActivity(intent);
            }
        });

        final Button memory = (Button) findViewById(R.id.buttonMemoire);
        memory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Option.this, MemoryController.class);
                startActivity(intent);
            }
        });

    }
}
