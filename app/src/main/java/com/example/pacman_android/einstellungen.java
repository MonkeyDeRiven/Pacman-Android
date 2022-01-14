package com.example.pacman_android;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myfirstapp.R;

public class einstellungen extends AppCompatActivity {
    private ImageButton xButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellungen);

        xButton = (ImageButton) findViewById(R.id.ibX);


        xButton.setOnClickListener(view ->{
            finish();
        });

    }
}