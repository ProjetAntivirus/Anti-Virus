package com.example.user.antivirus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.user.antivirus.Activity.Applications;
import com.example.user.antivirus.Activity.Option;
import com.example.user.antivirus.Activity.SMS;
import com.example.user.antivirus.Services.GestionBatteryService;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_antivirus);

        // Utile car le onBoot detecte uniquement le demarage de mon telephone nous devons donc le lancer manuellement
        Intent i = new Intent(MainActivity.this, GestionBatteryService.class);
        startService(i);

        final Button Composant = (Button) findViewById(R.id.buttonComposant);
        Composant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Option.class);
                startActivity(intent);
            }
        });


        final Button Flux = (Button) findViewById(R.id.buttonFlux);
        Flux.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SMS.class);
                startActivity(intent);
            }
        });

        final Button App = (Button) findViewById(R.id.buttonApp);
        App.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Applications.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_antivirus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
