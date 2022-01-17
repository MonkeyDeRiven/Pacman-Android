package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myfirstapp.R;

public class steuerungstutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steuerungstutorial);

        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitSteuerungstutorial);

        btnExit.setOnClickListener(view -> {
            finish();
        });
    }
}