package com.example.pacman_android;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myfirstapp.R;

public class GameActivity extends Activity {
    private GameView g;
    private Spielfeld s;
    private int score = 0;
    private TextView score_view;
    private TextView score_string;

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
            rotatePlayerRight();
        });

        btnUp_R.setOnClickListener(view -> {
            rotatePlayerUp();
        });

        btnDown_R.setOnClickListener(view -> {
            rotatePlayerDown();
        });
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

    public void rotatePlayerDown() {
        if (g.spieler.mundzu) {
            g.spieler.current = g.spieler.untenzu;
        } else {
            g.spieler.current = g.spieler.untenauf;
        }
        g.spieler.direction = 2;
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