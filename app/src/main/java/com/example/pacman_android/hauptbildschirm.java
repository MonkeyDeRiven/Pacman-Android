package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myfirstapp.R;

public class hauptbildschirm extends AppCompatActivity {
    private Button btnBestenliste;
    private Button btnHilfe;
    private Button btnEinstellungen;
    private Button btnSpielen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hauptbildschirm);
        btnBestenliste = (Button)findViewById(R.id.btnBestenliste);
        btnEinstellungen = (Button) findViewById(R.id.btnEinsellungen);
        btnHilfe = (Button) findViewById(R.id.btnHilfeSpielmenue);
        btnSpielen = (Button) findViewById(R.id.btnSpielen);

        btnEinstellungen.setOnClickListener(view ->{
            openActivityEinstellungen();
        });

        btnBestenliste.setOnClickListener(view -> {
            openActivityBestenliste();
        });

        btnSpielen.setOnClickListener(view -> {
            openActivitySpielen();
        });

        btnHilfe.setOnClickListener(view -> {
            openActivityHilfe();
        });
    }

    public void openActivitySpielen(){
        Intent spielenView = new Intent(this, GameActivity.class);
        startActivity(spielenView);
    }

    protected void openActivityEinstellungen(){
        Intent einstellungsView = new Intent(this, einstellungen.class);
        startActivity(einstellungsView);

    }

    protected void openActivityBestenliste(){
        Intent bestenlisteView = new Intent(this, bestenliste.class);
        startActivity(bestenlisteView);
    }

    public void openActivityHilfe(){
        Intent hilfeView = new Intent(this, hilfe.class);
        startActivity(hilfeView);
    }

}