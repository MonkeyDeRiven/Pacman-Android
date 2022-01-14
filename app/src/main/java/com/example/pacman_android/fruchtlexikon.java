package com.example.pacman_android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

public class fruchtlexikon extends AppCompatActivity {
    String s1[] = {"Kirsche","Eisfrucht","Ananas","Feuerfrucht"};
    String s2[] ={"Alle Geister im Labyrinth werden langsamer.",
            "Alle Geister gefrieren für x sec.",
            "200 Extra Punkte.",
            "Der PAC MAN brennt und ist schneller."};


    int images[] = {R.drawable.kirsche,R.drawable.eisfrucht,R.drawable.ananas,R.drawable.feuerfrucht_transparent};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geisterlexikon);
        RecyclerView rv= findViewById(R.id.recycleview);

        MyAdapter myAdapter = new MyAdapter(this,s1,s2,images);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));



    }
}

