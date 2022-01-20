package com.example.pacman_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myfirstapp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements RankingDialog.RankingDialogListener {
    private GameView g;
    private TextView pScore;
    private String userNameDone;
    int score;

    private TextView score_view;
    private TextView score_string;

    public static Handler h;


    private static final String filename = "highscore.txt";
    private ArrayList<bestenliste.player> arrBestenListe = new ArrayList<>();


    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        score = 0;

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
        gameEnd();
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

        //opens the settings file and provides a reader "settingsReader"
        InputStream settings = getResources().openRawResource(R.raw.settings);
        InputStreamReader inputReader = new InputStreamReader(settings);
        BufferedReader settingsReader = new BufferedReader(inputReader);

        String controller_layout = "left";
        try {
            //Reads a line
            controller_layout = settingsReader.readLine();

            //Formats the String
            controller_layout = controller_layout.substring(controller_layout.indexOf('=')+1);
            controller_layout = controller_layout.trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(controller_layout.equals("left")){
            //hide rigth controller
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
        else if(controller_layout.equals("right")){
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

    public void gameEnd(){
        loadRanking();
        addHighscore();

    }

    public void addHighscore(){
        int size = arrBestenListe.size();
        Boolean playerIsBetter = false;
        score = 500;
        for(int i = 0; i<size; i++){
            if(score >= arrBestenListe.get(i).score) {
                playerIsBetter = true;
                break;
            }
        }

        if(size == 0)
            playerIsBetter = true;

        if(playerIsBetter){
            openDialog();

        }
    }

    public void openDialog(){
        RankingDialog rankingdialog = new RankingDialog();
        rankingdialog.show(getSupportFragmentManager(), "Top5");
    }

    void saveFile(){
        FileOutputStream fos = null;
        String text = "";
        String name;
        String score;


        int size = arrBestenListe.size();

        for(int i = 0; i < size; i++){
            name = arrBestenListe.get(i).name;
            score = String.valueOf(arrBestenListe.get(i).score);
            text = text + name + ";" + score + "\n";
        }

        try {
            fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void loadRanking(){
        FileInputStream inputStream = null;

        String[] lineSplit;

        try{
            inputStream = openFileInput(filename);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(streamReader);
            String textLine;
            String name;
            int score;
            int counter = 0;

            while( (textLine = buffReader.readLine()) != null) {
                if (counter <= 4) {
                    lineSplit = textLine.split(";");
                    name = lineSplit[0];
                    score = Integer.parseInt(lineSplit[1]);
                    arrBestenListe.add(new bestenliste.player(name, score));
                    counter++;

                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUserName(String username) {
        userNameDone = username;
        arrBestenListe.add(new bestenliste.player(userNameDone, score));
        saveFile();
    }
}