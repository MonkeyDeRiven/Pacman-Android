package com.example.pacman_android;

import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    static class player{
        int score;
        String name;

        player(String name, int score){
            this.name = name;
            this.score = score;
        }
    }

    ArrayList <player> arrBestenListe = new ArrayList<>();

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

        loadData();
        sortArr();
        setUpBestenliste();
    }

    public void loadData(){
        FileInputStream fis = null;

        String[] lineSplit;

        try{
            fis = openFileInput(filename);
            InputStreamReader streamReader = new InputStreamReader(fis);
            BufferedReader buffReader = new BufferedReader(streamReader);
            String textLine;
            String name;
            int score;

            while( (textLine = buffReader.readLine()) != null){
                lineSplit = textLine.split(";");
                name = lineSplit[0];
                score = Integer.parseInt(lineSplit[1]);
                arrBestenListe.add(new player(name, score));
            }

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sortArr(){
        Collections.sort(arrBestenListe, (p1, p2) -> Integer.valueOf(p2.score).compareTo(p1.score));
    }

    void setUpBestenliste(){
        int size  = arrBestenListe.size();

        if(size >= 1) {
            posOneName.setText(arrBestenListe.get(0).name);
            String hilfsstring = String.valueOf(arrBestenListe.get(0).score);
            posOneScore.setText(hilfsstring);
        }

        if(size >= 2) {
            posTwoName.setText(arrBestenListe.get(1).name);
            String hilfsstring = String.valueOf(arrBestenListe.get(1).score);
            posTwoScore.setText(hilfsstring);
        }

        if(size >= 3) {
            posThreeName.setText(arrBestenListe.get(2).name);
            String hilfsstring = String.valueOf(arrBestenListe.get(2).score);
            posThreeScore.setText(hilfsstring);
        }

        if(size >= 4) {
            posFourName.setText(arrBestenListe.get(3).name);
            String hilfsstring = String.valueOf(arrBestenListe.get(3).score);
            posFourScore.setText(hilfsstring);
        }

        if(size >= 5) {
            posFiveName.setText(arrBestenListe.get(4).name);
            String hilfsstring = String.valueOf(arrBestenListe.get(4).score);
            posFiveScore.setText(hilfsstring);
        }
    }
}