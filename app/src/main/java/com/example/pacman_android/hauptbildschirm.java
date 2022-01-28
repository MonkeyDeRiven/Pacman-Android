package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class hauptbildschirm extends AppCompatActivity {
    private Button btnBestenliste;
    private Button btnHilfe;
    private Button btnEinstellungen;
    private Button btnSpielen;
    private ImageButton btnSkinauswahl;
    private ImageView pacman;
    public MediaPlayer mediaPlayer;
    public int skinanimater=0;
    Animation shake;
    AnimationDrawable pacmanmovement;
    AudioManager am;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hauptbildschirm);


        mediaPlayer = MediaPlayer.create(this, R.raw.pacmansong);
        mediaPlayer.start();

        btnBestenliste = (Button) findViewById(R.id.btnBestenliste);
        btnEinstellungen = (Button) findViewById(R.id.btnEinsellungen);
        btnHilfe = (Button) findViewById(R.id.btnHilfeSpielmenue);
        btnSpielen = (Button) findViewById(R.id.btnSpielen);
        btnSkinauswahl = (ImageButton) findViewById(R.id.skinauswahl);
        pacman = findViewById(R.id.pacmananimated);


        skinanimater = (int) (Math.random() * 3 - 0 + 1) + 0;
        if (skinanimater == 0) pacman.setBackgroundResource(R.drawable.pacmanmovementgelb);
        if (skinanimater == 1) pacman.setBackgroundResource(R.drawable.pacmanmovementblau);
        if (skinanimater == 2) pacman.setBackgroundResource(R.drawable.pacmanmovementgrun);
        if (skinanimater == 3) pacman.setBackgroundResource(R.drawable.pacmanmovementrot);
        pacmanmovement = (AnimationDrawable) pacman.getBackground();
        pacmanmovement.start();


        btnEinstellungen.setOnClickListener(view -> {
            Intent i = new Intent(this, einstellungen.class);
            animate(btnEinstellungen, i);


        });


        btnSkinauswahl.setOnClickListener(view -> {
            shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            Intent i = new Intent(this, skinliste.class);
            shake.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startActivity(i);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            btnSkinauswahl.startAnimation(shake);

        });

        btnBestenliste.setOnClickListener(view -> {
            Intent i = new Intent(this, bestenliste.class);
            animate(btnBestenliste, i);
        });

        btnSpielen.setOnClickListener(view -> {
            mediaPlayer.stop();
            mediaPlayer.release();
            TextView m = findViewById(R.id.m);
            TextView a = findViewById(R.id.a);
            TextView n = findViewById(R.id.n);
            ImageView kleiderbugel = findViewById(R.id.skinauswahl);
            Animation rightmove = AnimationUtils.loadAnimation(this, R.anim.heat2);
            ImageView pacman = findViewById(R.id.pacmananimated);
            pacman.startAnimation(rightmove);
            new CountDownTimer(900, 1200) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    m.setText("");
                }

            }.start();
            new CountDownTimer(1300, 1200) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    a.setText("");
                }

            }.start();
            new CountDownTimer(1700, 1200) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    n.setText("");
                }

            }.start();

            new CountDownTimer(2100, 1200) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    kleiderbugel.setVisibility(View.INVISIBLE);
                }

            }.start();



        new CountDownTimer(2300, 2300) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Intent i = new Intent(getBaseContext(), spielbildschirm.class);
                startActivity(i);
            }

        }.start();

            new CountDownTimer(2800, 2300) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    m.setText("m");a.setText("a");n.setText("n");kleiderbugel.setVisibility(View.VISIBLE);
                }

            }.start();

        });

        btnHilfe.setOnClickListener(view -> {
            Intent i = new Intent(this, hilfe.class);
            animate(btnHilfe, i);
        });

}


    public void openActivitySpielen(){
        Intent spielenView = new Intent(this, spielbildschirm.class);
        startActivity(spielenView);
    }
    public void openWardrobe()
    {Intent skinliste = new Intent(this, skinliste.class);
        startActivity(skinliste);


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

    protected void onResume() {
        super.onResume();
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



