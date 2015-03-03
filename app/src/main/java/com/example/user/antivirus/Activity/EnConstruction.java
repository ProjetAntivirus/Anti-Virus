package com.example.user.antivirus.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.user.antivirus.R;

// Classe avec aucune utilité, permettant d'afficher une page "En construction"
// lorsque la fonctionnalité n'a pas encore été réalisée
public class EnConstruction extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.construction);
    }
}
