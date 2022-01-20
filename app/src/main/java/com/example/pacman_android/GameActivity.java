package com.example.pacman_android;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myfirstapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GameActivity extends Activity {
    final String settingsFileName = "settings.txt";

    private GameView g;

    private int score = 0;
    private TextView score_view;
    private TextView score_string;

    public static Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // if you only need the surface view in full screen, you could also use this:
        // setContentView(new GameView(this));

        setContentView(R.layout.activity_game);

        // **  Add a TextView to display score ** //
        score_view = (TextView) findViewById(R.id.scorepoints);
        score_string=(TextView) findViewById(R.id.stringScore);


        // **  Add two buttons and add listener ** //
        g = findViewById(R.id.surfaceView);

        //moves the controller to one side, based on the settings.txt
        setControllerLayout();

        //Button for LEFT controller
        ImageButton btnRight_L = findViewById(R.id.btnRight_L);
        ImageButton btnLeft_L = findViewById(R.id.btnLeft_L);
        ImageButton btnUp_L =  findViewById(R.id.btnUp_L);
        ImageButton btnDown_L = findViewById(R.id.btnDown_L);

        //Button for RIGHT controller
        ImageButton btnRight_R = findViewById(R.id.btnRight_R);
        ImageButton btnLeft_R = findViewById(R.id.btnLeft_R);
        ImageButton btnUp_R = findViewById(R.id.btnUp_R);
        ImageButton btnDown_R = findViewById(R.id.btnDown_R);

        //OnClickListener for LEFT controller
        btnRight_L.setOnClickListener(view -> {
            rotatePlayerRight();
        });

        btnLeft_L.setOnClickListener(view -> {
            rotatePlayerLeft();
        });

        btnUp_L.setOnClickListener(view -> {
            rotatePlayerUp();
        });

        btnDown_L.setOnClickListener(view -> {
            rotatePlayerDown();
        });

        //OnClickListener for RIGHT controller

        btnRight_R.setOnClickListener(view -> {
            rotatePlayerRight();
        });

        btnLeft_R.setOnClickListener(view -> {
            rotatePlayerLeft();
        });

        btnUp_R.setOnClickListener(view -> {
            rotatePlayerUp();
        });

        btnDown_R.setOnClickListener(view -> {
            rotatePlayerDown();
        });

        h = new Handler() {
            public void handleMessage(Message msg) {
                finish();
            }
        };
    }

    public void rotatePlayerRight(){
        if(g.spieler.mundzu){
            g.spieler.current = g.spieler.rechtsauf;
        }
        else{
            g.spieler.current = g.spieler.rechtszu;
        }
        g.spieler.direction=1;
    }

    public void rotatePlayerLeft(){
        if(g.spieler.mundzu){
            g.spieler.current = g.spieler.linksauf;
        }
        else {
            g.spieler.current = g.spieler.linkszu;
        }
        g.spieler.direction=3;
    }

    public void rotatePlayerUp(){
        if(g.spieler.mundzu){
            g.spieler.current = g.spieler.obenauf;
        }
        else{
            g.spieler.current = g.spieler.obenzu;
        }
        g.spieler.direction=0;
    }

    public void rotatePlayerDown(){
        if(g.spieler.mundzu){
            g.spieler.current = g.spieler.untenzu;
        }
        else{
            g.spieler.current = g.spieler.untenauf;
        }
        g.spieler.direction=2;
    }


    public void setControllerLayout() {

        //Button for LEFT controller
        ImageButton btnRight_L = findViewById(R.id.btnRight_L);
        ImageButton btnLeft_L = findViewById(R.id.btnLeft_L);
        ImageButton btnUp_L =  findViewById(R.id.btnUp_L);
        ImageButton btnDown_L = findViewById(R.id.btnDown_L);

        //Button for RIGHT controller
        ImageButton btnRight_R = findViewById(R.id.btnRight_R);
        ImageButton btnLeft_R = findViewById(R.id.btnLeft_R);
        ImageButton btnUp_R = findViewById(R.id.btnUp_R);
        ImageButton btnDown_R = findViewById(R.id.btnDown_R);

        //Pause Button
        ImageButton btnPause = findViewById(R.id.btnPause);

        String controllerLayout = "left";

        try {
            FileInputStream settingsOutput = openFileInput(settingsFileName);
            InputStreamReader reader = new InputStreamReader(settingsOutput);
            BufferedReader settingsReader = new BufferedReader(reader);

            //reads line from settings.txt
            controllerLayout = settingsReader.readLine();

            //Formats the String
            controllerLayout = controllerLayout.substring(controllerLayout.indexOf('=')+1);
            controllerLayout = controllerLayout.trim();
            controllerLayout = controllerLayout.toLowerCase();

        } catch (IOException e) {
            Log.e("SETTINGS_ERROR" ,"Could not set controller layout, settings file may be corrupted");
        }

        if(controllerLayout.equals("left")){
            //hide right controller
            btnRight_R.setVisibility(View.GONE);
            btnLeft_R.setVisibility(View.GONE);
            btnUp_R.setVisibility(View.GONE);
            btnDown_R.setVisibility(View.GONE);

            //show left controller
            btnRight_L.setVisibility(View.VISIBLE);
            btnLeft_L.setVisibility(View.VISIBLE);
            btnUp_L.setVisibility(View.VISIBLE);
            btnDown_L.setVisibility(View.VISIBLE);

        }
        else if(controllerLayout.equals("right")){
            //hide rigth controller
            btnRight_R.setVisibility(View.VISIBLE);
            btnLeft_R.setVisibility(View.VISIBLE);
            btnUp_R.setVisibility(View.VISIBLE);
            btnDown_R.setVisibility(View.VISIBLE);

            //show left controller
            btnRight_L.setVisibility(View.GONE);
            btnLeft_L.setVisibility(View.GONE);
            btnUp_L.setVisibility(View.GONE);
            btnDown_L.setVisibility(View.GONE);
        }

        //OnClickListener for pause button
        btnPause.setOnClickListener(view -> {
            openSpielmenueActivity();
        });
    }

    public void openSpielmenueActivity(){
        Intent spielmenueView = new Intent(this, spielmenu.class);
        startActivity(spielmenueView);
    }

    public void update(double delta) {

        // Updates on UI-Elements (like all Views) have to be done by the main UI-thread
        // Hence:
        // ** Update your game logic here:


        // ** and then tell the UI-Thread to update the views whenever it pleases:
        runOnUiThread(() -> {
            score_view.setText("Score: " + (int) score);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        g.restart();
        setControllerLayout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        g.thread.setRunning(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}