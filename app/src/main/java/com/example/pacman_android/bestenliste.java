package com.example.pacman_android;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

public class bestenliste extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestenliste);

        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitBestenliste);

        btnExit.setOnClickListener(view -> {
            finish();
        });
    }
}