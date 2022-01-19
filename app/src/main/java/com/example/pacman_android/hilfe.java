package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ImageButton;
import android.widget.Button;

import android.content.Intent;

import com.example.myfirstapp.R;


public class hilfe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hilfe);

        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitHilfe);

        Button btnSteuerung = (Button) findViewById(R.id.btnSteuerungstutorial);
        Button btnGeisterlexikon = (Button) findViewById(R.id.btnGeisterlexikon);
        Button btnFruechtelexikon = (Button) findViewById(R.id.btnFruechtelexikon);

        btnExit.setOnClickListener(view->{
            finish();
        });

        btnSteuerung.setOnClickListener(view -> {
            Intent openActivitySteuerung = new Intent(this, steuerungstutorial.class);
            startActivity(openActivitySteuerung);
        });

        btnGeisterlexikon.setOnClickListener(view -> {
            Intent openActivityGeisterlexikon = new Intent(this, geisterlexikon.class);
            startActivity(openActivityGeisterlexikon);
        });

        btnFruechtelexikon.setOnClickListener(view -> {
            Intent openActivityFruechtelexikon = new Intent(this, fruchtlexikon.class);
            startActivity(openActivityFruechtelexikon);
        });
    }
}