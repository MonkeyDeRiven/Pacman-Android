package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.myfirstapp.R;

public class hauptbildschirm extends AppCompatActivity {
    private Button btnBestenliste;
    private Button btnHilfe;
    private Button btnEinstellungen;
    private Button btnSpielen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hauptbildschirm);
        btnBestenliste = (Button)findViewById(R.id.btnBestenliste);
        btnEinstellungen = (Button) findViewById(R.id.btnEinsellungen);

        btnEinstellungen.setOnClickListener(view ->{

            openActivityEinstellungen();

        });

        btnBestenliste.setOnClickListener(view -> {

            openActivityBestenliste();
        });
    }

    protected void openActivityEinstellungen(){
        Intent intent = new Intent(this, einstellungen.class);
        startActivity(intent);

    }


    protected void openActivityBestenliste(){
        Intent intent = new Intent(this, bestenliste.class);
        startActivity(intent);
    }

}