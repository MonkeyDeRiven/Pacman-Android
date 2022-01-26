package com.example.pacman_android;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

public class fruchtlexikon extends AppCompatActivity {
    String s1[] = {"Kirsche","Eisfrucht","Ananas","Feuerfrucht"};
    String s2[] ={"Alle Geister im Labyrinth werden zu langsame Schnecken.",
            "Alle Geister gefrieren für 5 sec und werden zu Schneemännern.",
            "200 Extra Punkte.",
            "Der PAC MAN brennt und ist schneller."};
    String s3[] = {"","","",""};


    int images[] = {R.drawable.kirsche,R.drawable.eisfrucht,R.drawable.ananas,R.drawable.feuerfrucht_transparent};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruchlexikon);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        RecyclerView rv= findViewById(R.id.recycleview);

        MyAdapter myAdapter = new MyAdapter(this,s1,s2,s3,images);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitFruechtelexikon);

        btnExit.setOnClickListener(view -> {
            finish();
        });

    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}

