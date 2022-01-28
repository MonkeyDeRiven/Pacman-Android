package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myfirstapp.R;


public class geisterlexikon extends AppCompatActivity {
RecyclerView recyclerView;
String s1[] = {"Blinky","Pinky","Inky","Clyde"};
String s2[] ={"Blinky ist der Verfolger. Er wird alles tun um so schnell wie möglich zu dir zu kommen, also behalte ihn lieber im Auge!",
        "Pinky liebt den Hinterhalt. Pinky ist weniger gefährlich,\nwenn du eine gute Distanz zu ihm hast,",
        "Inky ist sehr launisch. Man weiß nie wie seine aktuelle Laune gerade ist.",
        "Clyde ist nicht die hellste Kerze auf der Torte. Er verfolgt kein gewisses Ziel, bewegt sich dafür aber sehr schnell"};
String s3[] = {"", "aber nimm dich in Acht! Sobald du ihm zu nahe kommst\neröffnet er die Jagd auf dich.", "",""};


int images[] = {R.drawable.rotergeist,R.drawable.pinkergeist,R.drawable.blauergeist,R.drawable.orangegeist};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geisterlexikon);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        RecyclerView rv= findViewById(R.id.recycleview);

        MyAdapter myAdapter = new MyAdapter(this,s1,s2,s3,images);
        rv.setAdapter(myAdapter);
       rv.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitGeisterlexikon);

        btnExit.setOnClickListener(view -> {
            finish();
        });


    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}