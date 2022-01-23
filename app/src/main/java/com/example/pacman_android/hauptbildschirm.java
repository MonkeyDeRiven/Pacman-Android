package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import com.example.myfirstapp.R;

public class hauptbildschirm extends AppCompatActivity {
    private Button btnBestenliste;
    private Button btnHilfe;
    private Button btnEinstellungen;
    private Button btnSpielen;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hauptbildschirm);
        btnBestenliste = (Button)findViewById(R.id.btnBestenliste);
        btnEinstellungen = (Button) findViewById(R.id.btnEinsellungen);
        btnHilfe = (Button) findViewById(R.id.btnHilfeSpielmenue);
        btnSpielen = (Button) findViewById(R.id.btnSpielen);

         mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.app_src_main_res_raw_pacman_song);


        btnEinstellungen.setOnClickListener(view ->{
            openActivityEinstellungen();
        });

        btnBestenliste.setOnClickListener(view -> {
            openActivityBestenliste();
        });

        btnSpielen.setOnClickListener(view -> {
            openActivitySpielen();
        });

        btnHilfe.setOnClickListener(view -> {
            openActivityHilfe();
        });
    }

    public void openActivitySpielen(){
        Intent spielenView = new Intent(this, spielbildschirm.class);
        startActivity(spielenView);
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
        mediaPlayer.start();

    }


    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

}



