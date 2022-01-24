package com.example.pacman_android;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.myfirstapp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Locale;

public class einstellungen extends AppCompatActivity {
    private ImageButton xButton;
    final String settingsFileName = "settings.txt";

    private String controllerLayout = "left";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellungen);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setCurrentControllerLayout();
        setSwSwitch();

        xButton = (ImageButton) findViewById(R.id.ibX);

        xButton.setOnClickListener(view ->{
            finish();
        });

        Switch swControllerLayout = findViewById(R.id.swControllerLayout);

        swControllerLayout.setOnClickListener(view -> {
            toggleControllerLayout();
        });
    }

    //Reads the current controller layout from settings.txt
    //Puts the current controller orientation into the class attribute controllerLayout
    public void setCurrentControllerLayout(){

        String controllerLayoutLine = "";
        try{
            FileInputStream settingsOutput = openFileInput(settingsFileName);
            InputStreamReader reader = new InputStreamReader(settingsOutput);
            BufferedReader settingsReader = new BufferedReader(reader);

            controllerLayoutLine = settingsReader.readLine();

            //Formats the String
            controllerLayoutLine = controllerLayoutLine.substring(controllerLayoutLine.indexOf('=')+1);
            controllerLayoutLine = controllerLayoutLine.trim();
            controllerLayoutLine = controllerLayoutLine.toLowerCase();
        }catch (Exception e){
            Log.e("SETTINGS_ERROR" ,"Could not set controller layout, settings file may be corrupted");
        }

        if(controllerLayoutLine.equals("Left") || controllerLayoutLine.equals("right")){
            controllerLayout = controllerLayoutLine;
        }
    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    //Initialises controller switch based on settings.txt
    public void setSwSwitch(){
        Switch swControllerLayout = findViewById(R.id.swControllerLayout);

        if(controllerLayout.equals("left")){
            swControllerLayout.setChecked(false);
        }
        else if(controllerLayout.equals("right")){
            swControllerLayout.setChecked(true);
        }
    }

    //Toggles the controller layout and the controller layout switch.
    //Writes the new controller layout orientation into the settings file
    public void toggleControllerLayout(){
        Switch swControllerLayout = findViewById(R.id.swControllerLayout);

        if(controllerLayout.equals("left")){
            controllerLayout = "right";
            swControllerLayout.setChecked(true);
        }
        else if(controllerLayout.equals("right")){
            controllerLayout = "left";
            swControllerLayout.setChecked(false);
        }
    }

    public void safeSettings(){
        String controllerLayoutLabel = "controller_Layout = " + controllerLayout;
        try {
            FileOutputStream settingsFile = openFileOutput(settingsFileName, MODE_PRIVATE);
            settingsFile.write(controllerLayoutLabel.getBytes());
            Log.i("SUCESS", "Settings saved!");
        }catch(Exception e){
            Log.e("ERROR", "Could not save Settings");
        }
    }

    @Override
    public void onDestroy(){
        safeSettings();
        super.onDestroy();
    }
}