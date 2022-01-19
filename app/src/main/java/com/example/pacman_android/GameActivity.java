package com.example.pacman_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.myfirstapp.R;

public class GameActivity extends Activity {
    private GameView g;

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

        Button rb = (Button) findViewById(R.id.rightbutton);
        Button lb = (Button) findViewById(R.id.leftbutton);
        Button ub = (Button) findViewById(R.id.upbutton);
        Button bb = (Button) findViewById(R.id.downbutton);
        rb.setOnClickListener(
                view -> {
                    if(g.spieler.mundzu)g.spieler.current = g.spieler.rechtsauf;
                  else  g.spieler.current = g.spieler.rechtszu;

                    g.spieler.direction=1;

                });
        lb.setOnClickListener(
                view -> {
                    if(g.spieler.mundzu)g.spieler.current = g.spieler.linksauf;
                   else g.spieler.current = g.spieler.linkszu;
                    g.spieler.direction=3;
                });
        ub.setOnClickListener(
                view -> {
                    if(g.spieler.mundzu)g.spieler.current = g.spieler.obenauf;
                  else  g.spieler.current = g.spieler.obenzu;
                    g.spieler.direction=0;
                });
        bb.setOnClickListener(
                view -> {
                    if(g.spieler.mundzu)g.spieler.current = g.spieler.untenauf;
                    else g.spieler.current = g.spieler.untenzu;
                    g.spieler.direction=2;
                });
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