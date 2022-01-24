package com.example.pacman_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class skinliste extends AppCompatActivity {

    private Button gelbB;
    private Button grunB;
    private Button rotB;
    private Button blauB;
    private ImageButton exit;

    private ImageView gelb;
    private ImageView grun;
    private ImageView rot;
    private ImageView blau;


    AnimationDrawable grunA;
    AnimationDrawable blauA;
    AnimationDrawable rotA;
    AnimationDrawable gelbA;

    int auswahl = 0;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skinliste);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        auswahl = Integer.valueOf(readFromFile(this));

        gelbB= findViewById(R.id.gelb);
        rotB= findViewById(R.id.rot);
        grunB= findViewById(R.id.grun);
        blauB= findViewById(R.id.blau);

        gelb = findViewById(R.id.gelbbild);
        gelb.setBackgroundResource(R.drawable.pacmanmovementgelb);
        grun = findViewById(R.id.grunbild);
        grun.setBackgroundResource(R.drawable.pacmanmovementgrun);
        rot = findViewById(R.id.rotbild);
        rot.setBackgroundResource(R.drawable.pacmanmovementrot);
        blau = findViewById(R.id.blaubild);
        blau.setBackgroundResource(R.drawable.pacmanmovementblau);
        exit = findViewById(R.id.Exit);

        gelbA = (AnimationDrawable) gelb.getBackground();
        grunA = (AnimationDrawable) grun.getBackground();
        rotA = (AnimationDrawable) rot.getBackground();
        blauA = (AnimationDrawable) blau.getBackground();

        gelbA.start();
        grunA.start();
        rotA.start();
        blauA.start();

        if(auswahl ==0) {gelbB.setBackgroundColor(Color.GREEN);gelbB.setText("SELECTED");}
        if(auswahl ==1) {blauB.setBackgroundColor(Color.GREEN);blauB.setText("SELECTED");}
        if(auswahl ==2){grunB.setBackgroundColor(Color.GREEN);grunB.setText("SELECTED");}
        if(auswahl ==3) {rotB.setBackgroundColor(Color.GREEN);rotB.setText("SELECTED");}

     gelbB.setOnClickListener(view -> {
        auswahl = 0;
        gelbB.setBackgroundColor(Color.GREEN);
         gelbB.setText("SELECTED");

        grunB.setBackgroundColor(Color.YELLOW);
         blauB.setBackgroundColor(Color.YELLOW);
         rotB.setBackgroundColor(Color.YELLOW);

         grunB.setText("SELECT");
         blauB.setText("SELECT");
         rotB.setText("SELECT");

    });

      blauB.setOnClickListener(view -> {
       auswahl =1;
          blauB.setBackgroundColor(Color.GREEN);
          blauB.setText("SELECTED");

          grunB.setBackgroundColor(Color.YELLOW);
         gelbB.setBackgroundColor(Color.YELLOW);
          rotB.setBackgroundColor(Color.YELLOW);

          grunB.setText("SELECT");
          gelbB.setText("SELECT");
          rotB.setText("SELECT");
    });
       grunB.setOnClickListener(view -> {
        auswahl = 2;
        grunB.setBackgroundColor(Color.GREEN);
           grunB.setText("SELECTED");

          gelbB.setBackgroundColor(Color.YELLOW);
           blauB.setBackgroundColor(Color.YELLOW);
           rotB.setBackgroundColor(Color.YELLOW);

           gelbB.setText("SELECT");
           blauB.setText("SELECT");
           rotB.setText("SELECT");
    });
        rotB.setOnClickListener(view -> {
        auswahl = 3;
            rotB.setBackgroundColor(Color.GREEN);
            rotB.setText("SELECTED");

            grunB.setBackgroundColor(Color.YELLOW);
            blauB.setBackgroundColor(Color.YELLOW);
            gelbB.setBackgroundColor(Color.YELLOW);

            grunB.setText("SELECT");
            blauB.setText("SELECT");
            gelbB.setText("SELECT");

    });

        exit.setOnClickListener(view ->
        {
            writeToFile(String.valueOf(auswahl),this);
            Intent i= new Intent(this,hauptbildschirm.class);
        startActivity(i);

        });
}
    protected void onResume() {
        super.onResume();
        auswahl =Integer.valueOf(readFromFile(this));
        if(auswahl ==0) {gelbB.setBackgroundColor(Color.GREEN);gelbB.setText("SELECTED");}
        if(auswahl ==1) {blauB.setBackgroundColor(Color.GREEN);blauB.setText("SELECTED");}
        if(auswahl ==2){grunB.setBackgroundColor(Color.GREEN);grunB.setText("SELECTED");}
        if(auswahl ==3) {rotB.setBackgroundColor(Color.GREEN);rotB.setText("SELECTED");}
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("selectPacman.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            try (InputStream inputStream = context.openFileInput("selectPacman.txt")) {

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append("\n").append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return String.valueOf(ret.charAt(1));
    }
}

