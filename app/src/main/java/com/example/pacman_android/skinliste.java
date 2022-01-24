package com.example.pacman_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class skinliste extends AppCompatActivity {

    private Button gelb;
    private Button grun;
    private Button rot;
    private Button blau;
    private ImageButton exit;

    int auswahl = 0;

    protected void onCreate(Bundle savedInstanceState){
super.onCreate(savedInstanceState);
setContentView(R.layout.skinliste);

        gelb = findViewById(R.id.gelb);
        grun = findViewById(R.id.grun);
        rot = findViewById(R.id.rot);
        blau = findViewById(R.id.blau);
        exit = findViewById(R.id.Exit);


        if(auswahl ==0) {gelb.setBackgroundColor(Color.GREEN);gelb.setText("SELECTED");}
        if(auswahl ==1) {grun.setBackgroundColor(Color.GREEN);grun.setText("SELECTED");}
        if(auswahl ==2){blau.setBackgroundColor(Color.GREEN);blau.setText("SELECTED");}
        if(auswahl ==3) {rot.setBackgroundColor(Color.GREEN);rot.setText("SELECTED");}

     gelb.setOnClickListener(view -> {
        auswahl = 0;
        gelb.setBackgroundColor(Color.GREEN);
         gelb.setText("SELECTED");
        grun.setBackgroundColor(Color.YELLOW);
         blau.setBackgroundColor(Color.YELLOW);
         rot.setBackgroundColor(Color.YELLOW);
         grun.setText("SELECT");
         blau.setText("SELECT");
         rot.setText("SELECT");

    });

      blau.setOnClickListener(view -> {
       auswahl =1;
          blau.setBackgroundColor(Color.GREEN);
          blau.setText("SELECTED");
          grun.setBackgroundColor(Color.YELLOW);
         gelb.setBackgroundColor(Color.YELLOW);
          rot.setBackgroundColor(Color.YELLOW);
          grun.setText("SELECT");
          gelb.setText("SELECT");
          rot.setText("SELECT");
    });
       grun.setOnClickListener(view -> {
        auswahl = 2;
           grun.setBackgroundColor(Color.GREEN);
           grun.setText("SELECTED");
          gelb.setBackgroundColor(Color.YELLOW);
           blau.setBackgroundColor(Color.YELLOW);
           rot.setBackgroundColor(Color.YELLOW);
           gelb.setText("SELECT");
           blau.setText("SELECT");
           rot.setText("SELECT");
    });
        rot.setOnClickListener(view -> {
        auswahl = 3;
            rot.setBackgroundColor(Color.GREEN);
            rot.setText("SELECTED");
            grun.setBackgroundColor(Color.YELLOW);
            blau.setBackgroundColor(Color.YELLOW);
            gelb.setBackgroundColor(Color.YELLOW);
            grun.setText("SELECT");
            blau.setText("SELECT");
            gelb.setText("SELECT");

    });

        exit.setOnClickListener(view ->
        {
            writeToFile(String.valueOf(auswahl),this);
            Intent i= new Intent(this,hauptbildschirm.class);
        startActivity(i);

        });






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

}

