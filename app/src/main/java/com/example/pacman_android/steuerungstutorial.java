package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myfirstapp.R;

public class steuerungstutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steuerungstutorial);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        ImageButton btnExit = (ImageButton) findViewById(R.id.btnSteuerungstutorial);

        btnExit.setOnClickListener(view -> {
           finish();
        });
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}