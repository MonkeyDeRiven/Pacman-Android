package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

import com.example.myfirstapp.R;

public class spielmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spielmenu);

        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitSpielmenue);

        Button btnVerlassen = (Button) findViewById(R.id.btnVerlassen);
        Button btnEinstellungen = (Button) findViewById(R.id.btnEinstellungenSpielmenue);
        Button btnHilfe = (Button) findViewById(R.id.btnHilfeSpielmenue);

        btnVerlassen.setOnClickListener(view -> {
            finish();
            GameActivity.h.sendEmptyMessage(0);
        });

        btnEinstellungen.setOnClickListener(view -> {
            openEinstellungenActivity();
        });

        btnHilfe.setOnClickListener(view -> {
            openHilfeActivity();
        });

        btnExit.setOnClickListener(view -> {
            finish();
        });
    }

    public void openEinstellungenActivity(){
        Intent einstellungsView = new Intent(this, einstellungen.class);
        startActivity(einstellungsView);
    }

    public void openHilfeActivity(){
        Intent hilfeActivity = new Intent(this, hilfe.class);
        startActivity(hilfeActivity);
    }
}