package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Button;

import android.content.Intent;

import com.example.myfirstapp.R;


public class hilfe extends AppCompatActivity {
Animation shake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hilfe);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitHilfe);

        Button btnSteuerung = (Button) findViewById(R.id.btnSteuerungstutorial);
        Button btnGeisterlexikon = (Button) findViewById(R.id.btnGeisterlexikon);
        Button btnFruechtelexikon = (Button) findViewById(R.id.btnFruechtelexikon);

        btnExit.setOnClickListener(view->{
            finish();
        });

        btnSteuerung.setOnClickListener(view -> {
           Intent i = new Intent(this,steuerungstutorial.class);
            animate(btnSteuerung,i);
        });

        btnGeisterlexikon.setOnClickListener(view -> {
            Intent i = new Intent(this,geisterlexikon.class);
            animate(btnGeisterlexikon,i);
        });

        btnFruechtelexikon.setOnClickListener(view -> {
            Intent i = new Intent(this,fruchtlexikon.class);
            animate(btnFruechtelexikon,i);
        });
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void animate(Button button,Intent intentt)
    {
        shake= AnimationUtils.loadAnimation(this, R.anim.shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(intentt);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        button.startAnimation(shake);
    }
}