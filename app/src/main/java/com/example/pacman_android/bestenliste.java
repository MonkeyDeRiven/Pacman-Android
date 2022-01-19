package com.example.pacman_android;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

import java.io.FileInputStream;

public class bestenliste extends AppCompatActivity {
    TextView posOneName;
    TextView posOneScore;
    TextView posTwoName;
    TextView posTwoScore;
    TextView posThreeName;
    TextView posThreeScore;
    TextView posFourName;
    TextView posFourScore;
    TextView posFiveName;
    TextView posFiveScore;

    private static final String filename = "highscore.txt";

    class player{
        int score;
        String name;
    }

    private player[] bestenListe = new player[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestenliste);

        posOneName = (TextView) findViewById(R.id.posOneName);
        posOneScore = (TextView) findViewById(R.id.posOneScore);

        posTwoName = (TextView) findViewById(R.id.posTwoName);
        posTwoScore = (TextView) findViewById(R.id.posTwoScore);

        posThreeName = (TextView) findViewById(R.id.posThreeName);
        posThreeScore = (TextView) findViewById(R.id.posThreeScore);

        posFourName = (TextView) findViewById(R.id.posFourName);
        posFourScore = (TextView) findViewById(R.id.posFourScore);

        posFiveName = (TextView) findViewById(R.id.posFiveName);
        posFiveScore = (TextView) findViewById(R.id.posFiveScore);

        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitBestenliste);

        btnExit.setOnClickListener(view -> {
            finish();
        });
    }

    public void loadData(View v){
        FileInputStream fis = null;

        //fis = openFileInput(filename);


    }
}